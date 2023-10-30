package com.github.tjake.jlama.model.gpt2;
import com.github.tjake.jlama.safetensors.tokenizer.BPETokenizer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class GPT2Tokenizer extends BPETokenizer {

    private static BiMap<Integer, String> codePointsToByteStrings;
    private static BiMap<Integer, Integer> alteredBytes; // Codepoint and Token
    static {
        // https://github.com/openai/gpt-2/blob/master/src/encoder.py#L19
        alteredBytes = HashBiMap.create();
        int i = 0;
        for (int c = 0; c < 256; c++) {
            if ( (c < '!' || c > '~') && (c < '¡' || c > '¬') && ( c < '®' || c > 'ÿ')) {
                int codepoint = (i++ + 256);
                alteredBytes.put(c, codepoint);
            }
        }

        //Represent emojis as their badly tokenized strings
        codePointsToByteStrings = HashBiMap.create();
        for(int j = 9000; j <= 128512; j++) {
            if (Character.isEmoji(j)) {
                byte[] b = Character.toString(j).getBytes(StandardCharsets.UTF_8);
                StringBuilder sb = new StringBuilder();
                for (int k = 0; k < b.length; k++) {
                    String piece = Character.toString(Byte.toUnsignedInt(b[k]));
                    sb.append(piece);
                }
                codePointsToByteStrings.put(j, sb.toString());
            }
        }
    }

    public GPT2Tokenizer(Path modelPath) {
        super(modelPath);
    }

    @Override
    protected String preProcess(String sentence)
    {
        return sentence.codePoints()
                .map(c -> alteredBytes.getOrDefault(c, c))
                .mapToObj(Character::toString)
                .collect(Collectors.joining());
    }

    @Override
    protected long encodeCharacterAsToken(byte c) {
        int i = Byte.toUnsignedInt(c);
        Integer token = alteredBytes.getOrDefault(i, i);

        //Map to the actual byte characters token
        String s = Character.toString(token);
        Long b =  model.vocabLookup.get(s);
        return b == null ? token : b;
    }

    @Override
    protected Optional<Character> maybeDecodeTokenAsCharacter(long id) {
        return Optional.empty();
    }

    @Override
    public String decode(long id) {
        String s = model.vocabLookup.inverse().get(id);

        return s.codePoints()
                .map(c -> alteredBytes.inverse().getOrDefault(c, c))
                .mapToObj(Character::toString)
                .collect(Collectors.joining());
    }



    @Override
    protected String postProcess(String s) {

        for (Map.Entry<Integer, String> e : codePointsToByteStrings.entrySet()) {
            if (s.contains(e.getValue()))
                s = s.replace(e.getValue(), Character.toString(e.getKey()));
        }

        return s;
    }
}
