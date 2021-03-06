/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Abhishek
 */
public class printKeyPair {
    public static void main(String[] argsv) throws FileNotFoundException, IOException {
        List<Key> temp = new ArrayList<Key>();

        BufferedReader br = new BufferedReader(new FileReader(new File("topFreqKeys")));
        String line = "";
        while ((line = br.readLine()) != null) {
            String[] token = line.split("\t");
            Key k = new Key(token[0], Integer.parseInt(token[1]), token[2], countWords(token[0]));
            temp.add(k);
        }
        List<Key> removeList = new ArrayList<Key>();
        for (Key k : temp) {
            if (k.name.startsWith(" ")) {
                k.name = k.name.trim();
                for (Key p : temp) {
                    if (k.name.equalsIgnoreCase(p.name) && k.category.contains(p.category) && (k.freq != p.freq)) {
                        if (k.freq > p.freq) {
                            k.gKey.add(p);
                            k.freq = k.freq + p.freq;
                            removeList.add(p);
                        }
                        if (k.freq < p.freq) {
                            p.gKey.add(k);
                            p.freq = k.freq + p.freq;
                            removeList.add(k);                            
                        }
                    }
                }
            }
        }
        for (Key x : removeList) {
            if (x.gKey.isEmpty()) {
                temp.remove(x);
            } else {
                System.out.println("Error" + x.name);
            }
        }

        CosineSimilarity cs = new CosineSimilarity();
        for (int i = 0; i < temp.size(); i++) {
            for (int j = i + 1; j < temp.size(); j++) {
                double sim = cs.CosineSimilarity_Score(temp.get(i).name, temp.get(j).name);
                int ed = minDistance(temp.get(i).name, temp.get(j).name);
                if(temp.get(i).freq > temp.get(j).freq)
                    System.out.println(temp.get(i).name+"\t"+temp.get(j).name+"\t"+sim+"\t"+ed);
                else
                    System.out.println(temp.get(j).name+"\t"+temp.get(i).name+"\t"+sim+"\t"+ed);
            }
        }
    }

    public static int countWords(String s) {

        int wordCount = 0;

        boolean word = false;
        int endOfLine = s.length() - 1;

        for (int i = 0; i < s.length(); i++) {
            // if the char is a letter, word = true.
            if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
                word = true;
                // if char isn't a letter and there have been letters before,
                // counter goes up.
            } else if (!Character.isLetter(s.charAt(i)) && word) {
                wordCount++;
                word = false;
                // last word of String; if it doesn't end with a non letter, it
                // wouldn't count without this.
            } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
                wordCount++;
            }
        }
        return wordCount;
    }

    public static int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        // len1+1, len2+1, because finally return dp[len1][len2]
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        //iterate though, and check last char
        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                //if last two chars equal
                if (c1 == c2) {
                    //update dp value for +1 length
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replace = dp[i][j] + 1;
                    int insert = dp[i][j + 1] + 1;
                    int delete = dp[i + 1][j] + 1;

                    int min = replace > insert ? insert : replace;
                    min = delete > min ? min : delete;
                    dp[i + 1][j + 1] = min;
                }
            }
        }

        return dp[len1][len2];
    }
    
}
