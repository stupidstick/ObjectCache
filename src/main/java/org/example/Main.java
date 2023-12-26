package org.example;

import org.example.cache.CachingMethodFIFO;
import org.example.cache.CachingMethodLRU;
import org.example.cache.CachingMethodRAND;
import org.example.cache.CachingMethod;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        CachingMethod<String, Object> cache = new CachingMethodLRU<>(100);
        String filePath = "C:\\Users\\stupi\\IdeaProjects\\ObjectCache\\src\\main\\java\\org\\example\\cache\\text.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\W+");

                for (String word : words) {
                    cache.put(word, null);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(cache.keys());
        System.out.println(cache.statistic());
    }
}