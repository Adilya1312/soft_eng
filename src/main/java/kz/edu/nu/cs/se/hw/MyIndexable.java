package kz.edu.nu.cs.se.hw;

public class MyIndexable implements Indexable{
    private String word;
    private int lineNumber;
    MyIndexable(String word, int lineNumber){
        this.word=word;
        this.lineNumber=lineNumber;
    }
   // @Override
    public String getEntry() {
        return word;
    }

    //@Override
    public int getLineNumber() {
     return lineNumber;
    }


    public int compareTo(Indexable o) {
    if(this.word.compareTo(o.getEntry())==0){
        if(this.lineNumber>o.getLineNumber()){
            return 1;
        }
        else{
            return -1;
        }
    }
    return this.word.compareTo(o.getEntry());
    }
    public String toString(){
        return "["+word+":"+lineNumber+"]";
    }

    @Override
    public boolean equals(Indexable o){
        return (this.lineNumber==o.getLineNumber()&&this.word.equals(o.getEntry()));
    }

}
