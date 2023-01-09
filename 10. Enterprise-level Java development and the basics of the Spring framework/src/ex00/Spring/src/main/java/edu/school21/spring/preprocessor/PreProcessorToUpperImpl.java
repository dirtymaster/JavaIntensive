package edu.school21.spring.preprocessor;

public class PreProcessorToUpperImpl implements PreProcessor {

    @Override
    public String process(String text) {
        return text.toUpperCase();
    }
}
