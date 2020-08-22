package kz.edu.nu.cs.se.hw;
import java.io.*;
import java.util.*;

public class MyKeywordInContext implements KeywordInContext {
    private ArrayList<Indexable> items = new ArrayList<Indexable>(); //list of Indexable items
    private ArrayList<String> stopArr = new ArrayList<String>(); //list of stopwords
    private ArrayList<String> indLines = new ArrayList<String>();
    private ArrayList<String> text = new ArrayList<String>();
    private String html;
    private String txtFile;

    public MyKeywordInContext(String name, String pathstring) {
        this.html = "" + name + ".html";
        this.txtFile = pathstring;
    }

    @Override
    public int find(String word) {
        word = word.toLowerCase();
        for (Indexable item : this.items) {
            if (item.getEntry().equals(word)) {
                return item.getLineNumber();
            }
        }
        return -1;
    }

    @Override
    public Indexable get(int i) {
        Indexable res = null;
        for (Indexable item : this.items)
            if (item.getLineNumber() == i) {
                res = item;
                break;
            }
        return res;
    }

    @Override
    public void txt2html() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.txtFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.html));
            bw.write("<!DOCTYPE html>");
            bw.newLine();
            bw.write("<html><head><meta charset=\"UTF-8\"></head><body>");
            bw.newLine();
            bw.write("<div>");
            bw.newLine();
            String line;
            int i = 1;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.write("<span id=line_" + i + ">&nbsp&nbsp[" + i + "]</span><br>");
                bw.newLine();
                i++;
            }
            bw.write("</div></body></html>");
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void stopWords() {
        //reading stop words from txt file.
        // Stop words are written in different lines in "stopwords.txt"
        // stop words are in lowercase
        try {
            FileReader fstop = new FileReader("stopwords.txt");
            BufferedReader bstop = new BufferedReader(fstop);
            String stop;
            while ((stop = bstop.readLine()) != null) {
                this.stopArr.add(stop);
            }
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }


    @Override
    public void indexLines() {
        //created sorted ArrayList of Indexable items- items
        //created Arraylist of Lines -text
        try {
            this.stopWords();
            BufferedReader bsample = new BufferedReader(new FileReader(this.txtFile));
            String line;
            String[] words = null;
            int i = 1;
            while ((line = bsample.readLine()) != null) {
                this.text.add(line);
                line = line.replaceAll("\\p{P}", "");
                words = line.split(" ");
                for (String word : words) {
                    word=word.toLowerCase();
                    if (!this.stopArr.contains(word)) {
                        Indexable item = new MyIndexable(word, i);
                        this.items.add(item);
                        Collections.sort(this.items);
                    }
                }
                i++;
            }
            System.out.println(items);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //created kwic without links and Uppercase items
        for (Indexable item : this.items) {
            indLines.add(text.get((item.getLineNumber() - 1)) + "\n");

        }
        System.out.println(indLines);

    }

    @Override
    public void writeIndexToFile() {
        try {
            BufferedWriter bw2 = new BufferedWriter(new FileWriter("kwic-frankenstein.html"));
            bw2.write("<!DOCTYPE html>");
            bw2.newLine();
            bw2.write("<html><head><meta charset=\"UTF-8\"></head><body><div style=\"text-align:center;line-height:1.6\">");
            bw2.newLine();
            bw2.write("<div>");
            bw2.newLine();
            String line;
            int i=0;
            for (Indexable item : this.items) {
                line=indLines.get(i).replaceAll(item.getEntry(), "<a href=\"" + this.txtFile + "#line_"
                        + item.getLineNumber() + "\">" + item.getEntry().toUpperCase() + "</a>");
                line=line+"<br>";
                i++;
                bw2.write(line);
                bw2.newLine();
            }
            bw2.write("</div></body></html>");
            bw2.flush();
            bw2.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}