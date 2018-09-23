

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import zemberek.morphology.TurkishMorphology;
import zemberek.morphology.analysis.AnalysisFormatters;
import zemberek.morphology.analysis.SingleAnalysis;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.tokenization.TurkishSentenceExtractor;

public class Ana  {
    static ArrayList<String> cevaplar=new ArrayList();
    private static String[] soruKelimeler;

    public static String cevapla(String soru,List<String> cumleler){


        int sayac=0;
        double agirlik=0;
        List<Integer> sayaclar=new ArrayList();

        soruKelimeler = soru.split(" ");


        for (String cumle : cumleler) {
            sayac = 0;
            for (String kelime : soruKelimeler) {
                if (cumle.contains(kokal(kelime))) {
                    sayac++;
                }

            }
            sayaclar.add(sayac);
        }

        int enBuyukIndeks= sayaclar.indexOf(Collections.max(sayaclar));
        System.out.println(soru);
        System.out.println();
        System.out.println(cumleler.get(enBuyukIndeks));


        if(soru.contains("doğ")||soru.contains("öl")){
            return dogumOlumAl(cumleler.get(enBuyukIndeks));
        }
        else if (soru.contains("ne zaman") || soru.contains("Ne zaman") || soru.contains("yüzyıl")) {

            return zamanSorusuCevapla(soru, cumleler.get(enBuyukIndeks));

        }
        else{
            cevaplar.add(cumleler.get(enBuyukIndeks));

            return cumleler.get(enBuyukIndeks);
        }
    }


    public static String dogumOlumAl(String metin) {

        Matcher m;

        String regex1 = "\\(d  (\\d+ (ocak|Ocak|şubat|Şubat|mart|Mart|nisan|Nisan|mayıs|Mayıs|haziran|Haziran|temmuz|Temmuz|ağustos|Ağustos|eylül|Eylül|ekim|Ekin|kasım|Kasım|aralık|Aralık) \\d+) - ö, (\\d+ (ocak|Ocak|şubat|Şubat|mart|Mart|nisan|Nisan|mayıs|Mayıs|haziran|Haziran|temmuz|Temmuz|ağustos|Ağustos|eylül|Eylül|ekim|Ekin|kasım|Kasım|aralık|Aralık) \\d+)\\)";
        String regex2 = "\\(d  (\\d+) - ö, (\\d+[?]?)\\)";
        String regex3 = "\\(d  (\\d+), ([a-zA-ZığöçşİĞÜÇŞ]+)\\)";
        String regex4 = "\\(d  (\\d+), ([a-zA-ZZığöçşİĞÜÇŞ]+)[-] ö, (\\d+ (ocak|Ocak|şubat|Şubat|mart|Mart|nisan|Nisan|mayıs|Mayıs|haziran|Haziran|temmuz|Temmuz|ağustos|Ağustos|eylül|Eylül|ekim|Ekin|kasım|Kasım|aralık|Aralık) \\d+)[-] ([a-zA-ZZığöçşİĞÜÇŞ]+)\\)";
        String regex5 = "\\(d  ([a-zA-ZığöçşİĞÜÇŞ]+) (\\d+/\\d+), ö, ([a-zA-ZığöçşİĞÜÇŞ])+ (\\d+/\\d+)\\)";
        String regex6 = "\\(d  (\\d+ (ocak|Ocak|şubat|Şubat|mart|Mart|nisan|Nisan|mayıs|Mayıs|haziran|Haziran|temmuz|Temmuz|ağustos|Ağustos|eylül|Eylül|ekim|Ekin|kasım|Kasım|aralık|Aralık) \\d+); ([a-zA-ZığöçşİĞÜÇ, Ş]+) - ö, (\\d+ (ocak|Ocak|şubat|Şubat|mart|Mart|nisan|Nisan|mayıs|Mayıs|haziran|Haziran|temmuz|Temmuz|ağustos|Ağustos|eylül|Eylül|ekim|Ekin|kasım|Kasım|aralık|Aralık) \\d+); ([a-zA-ZığöçşİĞÜÇ, Ş]+)\\)";
        String regex7 = "\\(d  (\\d+); ([a-zA-ZığöçşİĞÜÇŞ, ]+)\\)";
        String regex8 = "\\(d  (\\d+/\\d+), ö, (\\d+/\\d+)\\)";
        String regex9 = "\\(d  (\\d+ (ocak|Ocak|şubat|Şubat|mart|Mart|nisan|Nisan|mayıs|Mayıs|haziran|Haziran|temmuz|Temmuz|ağustos|Ağustos|eylül|Eylül|ekim|Ekin|kasım|Kasım|aralık|Aralık) \\d+), ([a-zA-ZığöçşİĞÜÇŞ]+)\\)";
        String regex10 = "\\d+ (ocak|Ocak|şubat|Şubat|mart|Mart|nisan|Nisan|mayıs|Mayıs|haziran|Haziran|temmuz|Temmuz|ağustos|Ağustos|eylül|Eylül|ekim|Ekin|kasım|Kasım|aralık|Aralık) \\d+";

        if (Pattern.compile(regex1).matcher(metin).find()) {
            m=Pattern.compile(regex1).matcher(metin);
            m.find();
            cevaplar.add("D " + m.group(1) + " Ö " + m.group(3));
            return "D " + m.group(1) + " Ö " + m.group(3);
        }
        else if (Pattern.compile(regex2).matcher(metin).find()) {
            m=Pattern.compile(regex2).matcher(metin);
            m.find();
            cevaplar.add("D " + m.group(1) + " Ö " + m.group(2));
            return "D " + m.group(1) + " Ö " + m.group(2);
        }
        else if (Pattern.compile(regex3).matcher(metin).find()) {
            m=Pattern.compile(regex3).matcher(metin);
            m.find();
            cevaplar.add("D- " + m.group(1));
            return "D- " + m.group(1);
        }
        else if (Pattern.compile(regex4).matcher(metin).find()) {
            m=Pattern.compile(regex4).matcher(metin);
            m.find();
            cevaplar.add("D-" + m.group(1) + " Ö- " + m.group(3));
            return "D-" + m.group(1) + " Ö- " + m.group(3);
        }
        else if (Pattern.compile(regex5).matcher(metin).find()) {
            m=Pattern.compile(regex5).matcher(metin);
            m.find();
            cevaplar.add("D- " + m.group(2) + " Ö- " + m.group(4));
            return "D- " + m.group(2) + " Ö- " + m.group(4);
        }
        else if (Pattern.compile(regex6).matcher(metin).find()) {
            m=Pattern.compile(regex6).matcher(metin);
            m.find();
            cevaplar.add("D- " + m.group(1) + "Ö-" +m.group(4));
            return "D- " + m.group(1) + "Ö-" +m.group(4);
        }
        else if (Pattern.compile(regex7).matcher(metin).find()) {
            m=Pattern.compile(regex7).matcher(metin);
            m.find();
            cevaplar.add("D- " + m.group(1));
            return "D- " + m.group(1);
        }
        else if (Pattern.compile(regex8).matcher(metin).find()) {
            m=Pattern.compile(regex8).matcher(metin);
            m.find();
            cevaplar.add("D- " +m.group(1) + " Ö- " + m.group(2));
            return "D- " +m.group(1) + " Ö- " + m.group(2);
        }
        else if (Pattern.compile(regex9).matcher(metin).find()) {
            m=Pattern.compile(regex9).matcher(metin);
            m.find();
            cevaplar.add("D- " +m.group(1));
            return "D- " +m.group(1);
        }
        else if (Pattern.compile(regex10).matcher(metin).find()) {
            m=Pattern.compile(regex10).matcher(metin);
            m.find();
            cevaplar.add("D-" + m.group(0));
            return "D-" + m.group(0);
        }
        else {
            cevaplar.add(metin);
            return metin;
        }

    }


    private static String zamanSorusuCevapla(String soru,String cumle){
        Matcher m = Pattern.compile("\\d\\d\\d\\d+").matcher(cumle);



        if(soru.contains("ne zaman")){
            if(Pattern.compile("\\d\\d\\d\\d+").matcher(cumle).find()){

                m.find();
                String cevap=""+m.group(0);
                cevaplar.add(cevap);
                return  cevap;
            }
            else if (cumle.contains("yüzyıl")){
                String cevap="";
                if(cumle.indexOf("yüzyıl") - 5<0) {
                    cevap = cumle.substring(0, cumle.indexOf("yüzyıl") + 6);
                }
                else if(cumle.indexOf("yüzyıl") + 6>cumle.length()+1){
                    cevap = cumle.substring(cumle.indexOf("yüzyıl") - 5,0);
                }
                else {
                    cevap = cumle.substring(cumle.indexOf("yüzyıl") - 5, cumle.indexOf("yüzyıl") + 6);
                }
                cevap=cevap.replaceAll("(\\d+)\\/(\\d+)","$1./$2.");
                cevaplar.add(cevap);
                return  cevap;
            }
            else if(cumle.contains("yıl sonra")||cumle.contains("yıl önce")){
                String cevap=cumle.substring(cumle.indexOf("yıl")-3,cumle.indexOf("yıl")+9);
                cevaplar.add(cevap);
                return  cevap;
            }

            else if(cumle.contains("zaman")){
                cumle=cumle.split("zaman")[0];
                cevaplar.add(cumle+" zamanda");
                return  cumle+" zamanda";
            }

            else{
                cevaplar.add(cumle);
                return cumle;
            }

        }
        else if(soru.contains("yüzyıl")){

            if(cumle.indexOf("yüzyıl")-5>=0&&cumle.indexOf("yüzyıl")+5<=cumle.length()) {

                String cevap = cumle.substring(cumle.indexOf("yüzyıl") - 5, cumle.indexOf("yüzyıl") + 6);
                cevap = cevap.replaceAll("(\\d+)\\/(\\d+)", "$1./$2.");
                cevaplar.add(cevap);
                return cevap;
            }

        }
        return "";

    }

    public static List<String> simpleSentenceBoundaryDetector(String metin) {
        metin=normallestir(metin);
        TurkishSentenceExtractor extractor = TurkishSentenceExtractor.DEFAULT;
        List<String> cumleler = extractor.fromParagraph(metin);

        return  cumleler;
    }





    private static String normallestir(String metin){
        /*TurkishTokenizer tokenizer = TurkishTokenizer.ALL;
        TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
        TurkishSpellChecker spellChecker=null;
        try {
            spellChecker = new TurkishSpellChecker(morphology);
        }
        catch (Exception h){
            h.printStackTrace();
        }
        StringBuilder output = new StringBuilder();

        for (Token token : tokenizer.tokenize(metin)) {
            String text = token.getText();
            if (analyzeToken(token) && !spellChecker.check(text)) {
                List<String> strings = spellChecker.suggestForWord(token.getText());
                if (!strings.isEmpty()) {
                    String suggestion = strings.get(0);
                    output.append(suggestion);
                } else {
                    output.append(text);
                }
            } else {
                output.append(text);
            }
        }
        return sayilariDuzelt(output.toString());
        */
        return metin;
    }





    private static String sayilariDuzelt(String metin){
        String duzenli=metin.replace(" bir "," 1 ").toLowerCase();
        duzenli=duzenli.replace(" iki "," 2 ");
        duzenli=duzenli.replace(" üç "," 3 ");
        duzenli=duzenli.replace(" dört "," 4 ");
        duzenli=duzenli.replace(" beş "," 5 ");
        duzenli=duzenli.replace(" altı "," 6 ");
        duzenli=duzenli.replace(" yedi "," 7 ");
        duzenli=duzenli.replace(" sekiz "," 8 ");
        duzenli=duzenli.replace(" dokuz "," 9 ");
        duzenli=duzenli.replace(" on "," 10 ");
        duzenli=duzenli.replace(" on bir "," 11 ");
        duzenli=duzenli.replace(" on iki "," 12 ");
        duzenli=duzenli.replace(" on üç "," 13 ");
        duzenli=duzenli.replace(" on dört "," 14 ");
        duzenli=duzenli.replace(" on beş "," 15 ");
        duzenli=duzenli.replace(" on altı "," 16 ");
        duzenli=duzenli.replace(" on yedi"," 17 ");
        duzenli=duzenli.replace(" on sekiz "," 18 ");
        duzenli=duzenli.replace(" on dokuz"," 19 ");
        duzenli=duzenli.replace(" yirmi "," 20 ");
        duzenli=duzenli.replace(" yirmi bir "," 21 ");
        duzenli=duzenli.replace(" yirmi iki "," 22 ");
        duzenli=duzenli.replace(" yirmi üç "," 23 ");
        duzenli=duzenli.replace(" yirmi dört "," 24 ");
        duzenli=duzenli.replace(" yirmi beş "," 25 ");
        duzenli=duzenli.replace(" yirmi altı "," 26 ");
        duzenli=duzenli.replace(" yirmi yedi "," 27 ");
        duzenli=duzenli.replace(" yirmi sekiz "," 28 ");
        duzenli=duzenli.replace(" yirmi dokuz "," 29 ");
        duzenli=duzenli.replace(" otuz "," 30 ");
        duzenli=duzenli.replace(" otuz bir "," 31 ");
        duzenli=duzenli.replace(" otuz iki "," 32 ");
        duzenli=duzenli.replace(" otuz üç "," 33 ");
        duzenli=duzenli.replace(" otuz dört "," 34 ");
        duzenli=duzenli.replace(" otuz beş "," 35 ");
        duzenli=duzenli.replace(" otuz altı "," 36 ");
        duzenli=duzenli.replace(" otuz yedi "," 37 ");
        duzenli=duzenli.replace(" otuz sekiz "," 38 ");
        duzenli=duzenli.replace(" otuz dokuz "," 39 ");
        duzenli=duzenli.replace(" kırk "," 40 ");
        duzenli=duzenli.replace(" kırk bir "," 41 ");
        duzenli=duzenli.replace(" kırk iki "," 42 ");
        duzenli=duzenli.replace(" kırk üç "," 43 ");
        duzenli=duzenli.replace(" kırk dört "," 44 ");
        duzenli=duzenli.replace(" kırk beş "," 45 ");
        duzenli=duzenli.replace(" kırk altı "," 46 ");
        duzenli=duzenli.replace(" kırk yedi "," 47 ");
        duzenli=duzenli.replace(" kırk sekiz "," 48 ");
        duzenli=duzenli.replace(" kırk dokuz "," 49 ");
        duzenli=duzenli.replace(" elli "," 50 ");
        duzenli=duzenli.replace(" elli bir "," 51 ");
        duzenli=duzenli.replace(" elli iki "," 52 ");
        duzenli=duzenli.replace(" elli üç "," 53 ");
        duzenli=duzenli.replace(" elli dört "," 54 ");
        duzenli=duzenli.replace(" elli beş "," 55 ");
        duzenli=duzenli.replace(" elli altı "," 56 ");
        duzenli=duzenli.replace(" elli yedi "," 57 ");
        duzenli=duzenli.replace(" elli sekiz "," 58 ");
        duzenli=duzenli.replace(" elli dokuz "," 59 ");
        duzenli=duzenli.replace(" atmış "," 60 ");
        duzenli=duzenli.replace(" atmış bir "," 61 ");
        duzenli=duzenli.replace(" atmış iki "," 62 ");
        duzenli=duzenli.replace(" atmış üç "," 63 ");
        duzenli=duzenli.replace(" atmış dört "," 64 ");
        duzenli=duzenli.replace(" atmış beş "," 65 ");
        duzenli=duzenli.replace(" atmış altı "," 66 ");
        duzenli=duzenli.replace(" atmış yedi "," 67 ");
        duzenli=duzenli.replace(" atmış sekiz "," 68 ");
        duzenli=duzenli.replace(" atmış dokuz "," 69 ");
        duzenli=duzenli.replace(" yetmiş "," 70 ");
        duzenli=duzenli.replace(" yetmiş bir "," 71 ");
        duzenli=duzenli.replace(" yetmiş iki "," 72 ");
        duzenli=duzenli.replace(" yetmiş üç "," 73 ");
        duzenli=duzenli.replace(" yetmiş dört "," 74 ");
        duzenli=duzenli.replace(" yetmiş beş "," 75 ");
        duzenli=duzenli.replace(" yetmiş altı "," 76 ");
        duzenli=duzenli.replace(" yetmiş yedi "," 77 ");
        duzenli=duzenli.replace(" yetmiş sekiz "," 78 ");
        duzenli=duzenli.replace(" yetmiş dokuz "," 79 ");
        duzenli=duzenli.replace(" seksen "," 80 ");
        duzenli=duzenli.replace(" seksen bir "," 81 ");
        duzenli=duzenli.replace(" seksen iki "," 82 ");
        duzenli=duzenli.replace(" seksen üç "," 83 ");
        duzenli=duzenli.replace(" seksen dört "," 84 ");
        duzenli=duzenli.replace(" seksen beş "," 85 ");
        duzenli=duzenli.replace(" seksen altı "," 86 ");
        duzenli=duzenli.replace(" seksen yedi "," 87 ");
        duzenli=duzenli.replace(" seksen sekiz "," 88 ");
        duzenli=duzenli.replace(" seksen dokuz "," 89 ");
        duzenli=duzenli.replace(" doksan "," 90 ");
        duzenli=duzenli.replace(" doksan bir "," 91 ");
        duzenli=duzenli.replace(" doksan iki "," 92 ");
        duzenli=duzenli.replace(" doksan üç "," 93 ");
        duzenli=duzenli.replace(" doksan dört "," 94 ");
        duzenli=duzenli.replace(" doksan beş "," 95 ");
        duzenli=duzenli.replace(" doksan altı "," 96 ");
        duzenli=duzenli.replace(" doksan yedi "," 97 ");
        duzenli=duzenli.replace(" doksan sekiz "," 98 ");
        duzenli=duzenli.replace(" doksan dokuz "," 99 ");
        duzenli=duzenli.replace(" yüz "," 100 ");

        return duzenli;
    }


    private static void dosyayaYaz(ArrayList<String> cevap,String paragraf){
        //cevap+="\\n";



        ArrayList<String> cevapBaslangiclari=new ArrayList();


        for(String cevaplar:cevap){
            cevapBaslangiclari.add(paragraf.indexOf(cevaplar)+"");
        }


    }




    private static String[] soruBul(){
        File dosya=new File("/Users/ahmedselimuzum/IdeaProjects/Zemberek/src/soru.txt");
        try{
            Scanner tarayici=new Scanner(dosya);
            StringBuilder dosyaStr=new StringBuilder();

            while(tarayici.hasNextLine()){
                dosyaStr.append(tarayici.nextLine());
            }

            String[] sorularDizi=dosyaStr.toString().replaceAll("SONLANDIR","").split("\\\\n");

            return sorularDizi;
        }
        catch (Exception h){
            h.printStackTrace();
        }
        return null;
    }



    private static String paragrafVeSoruBul(){
        File dosya=new File("/Users/ahmedselimuzum/IdeaProjects/TeknofestYarismasi/soru.json");



        try{
            Scanner tarayici=new Scanner(dosya);
            StringBuilder jsonStr=new StringBuilder();

            while(tarayici.hasNextLine()){
                jsonStr.append(tarayici.nextLine());
            }

            JSONParser jsonDonusturucu=new JSONParser();
            JSONObject json=(JSONObject) jsonDonusturucu.parse(jsonStr.toString());

            JSONArray veri=(JSONArray) json.get("veri");


            JSONObject o;
            JSONObject o2;
            JSONObject o3;
            JSONArray a;
            JSONArray a2;
            String metin;
            String cevap;
            String cevapBaslangici;
            File dosya2=new File("/Users/ahmedselimuzum/IdeaProjects/TeknofestYarismasi/cevap.json");
            for(int i=0;i<veri.size();i++){
                o=(JSONObject) veri.get(i);
                a=(JSONArray) o.get("paragraflar");


                for(int i2=0;i2<a.size();i2++){

                    o2=(JSONObject) a.get(i2);
                    a2=(JSONArray) o2.get("soru_cevaplar");
                    metin=(String) o2.get("paragraf_metni");
                    for(int i3=0;i3<a2.size();i3++){
                        o3=(JSONObject) a2.get(i3);
                        metin = sayilariDuzelt(metin.replaceAll("\\(d\\.", "(d ").replaceAll("ö\\.", "ö,").replaceAll("(\\d+)[. ]*\\/[ ]*(\\d+)\\.", "$1/$2").replaceAll("altmış", "atmış").replaceAll("[(]([a-zöğıçüş])\\.(\\d+)[)]", "($1 $2)"));
                        cevap=cevapla(normallestir((String) o3.get("soru")), simpleSentenceBoundaryDetector(metin));
                        cevap=cevap.replace("\\/","/");
                        cevapBaslangici=(metin).toLowerCase().indexOf(cevap.toLowerCase())+"";
                        System.out.println("***********************");
                        System.out.println("***********************");
                        System.out.println(o3.get("soru").toString());
                        System.out.println(cevap);
                        System.out.println("***********************");
                        System.out.println("***********************");

                        o3.put("cevap_baslangici",cevapBaslangici);
                        o3.put("cevap",cevap);


                        try (PrintWriter yazici = new PrintWriter(dosya2)) {
                            yazici.write(json.toJSONString());
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }


            }






            tarayici.close();


            System.out.println(json.toJSONString());


            try (PrintWriter yazici = new PrintWriter(dosya2)) {
                yazici.write(json.toJSONString());
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        catch(Exception h){
            h.printStackTrace();
        }
        return null;
    }





    private static String paragrafBul(String soru){
        File dosya=new File("/Users/ahmedselimuzum/IdeaProjects/Zemberek/src/object4.json");

        try{
            Scanner tarayici=new Scanner(dosya);
            StringBuilder jsonStr=new StringBuilder();
            while(tarayici.hasNextLine()){
                jsonStr.append(tarayici.nextLine());
            }

            JSONParser jsonDonusturucu=new JSONParser();
            JSONObject okunanJSON=(JSONObject) jsonDonusturucu.parse(jsonStr.toString());
            JSONArray dizi=(JSONArray) okunanJSON.get("data");


            JSONObject o;
            JSONObject o2;
            JSONArray a;
            StringBuilder paragraflar=new StringBuilder();
            try {

                for (int i = 0; i < dizi.size(); i++) {
                    o = (JSONObject) dizi.get(i);
                    if (soru.toLowerCase().contains(((String) o.get("title")).toLowerCase())) {

                        if (soru.substring(soru.length() - 2, soru.length() - 1) == " ") {
                            soru = soru.substring(0, soru.length() - 1);
                        }

                        a = (JSONArray) o.get("paragraphs");

                        for (int b = 0; b < a.size(); b++) {
                            o2 = (JSONObject) a.get(b);
                            paragraflar.append((String) o2.get("context"));
                        }



                        return paragraflar.toString();
                    }
                }

            }
            catch (NullPointerException h){

                o=(JSONObject) dizi.get(20);
                a = (JSONArray) o.get("paragraphs");

                for (int b = 0; b < a.size(); b++) {
                    o2 = (JSONObject) a.get(b);
                    paragraflar.append((String) o2.get("context"));
                }
                return paragraflar.toString();

            }
        }
        catch (Exception h){
            h.printStackTrace();
        }

        return "";




    }


    public static void main(String[] args) {
        //String[] dizi=soruBul();
        /*ArrayList<String> dizi=new ArrayList<>();

        Scanner tarayici=new Scanner(System.in);

        System.out.println("***********************");
        System.out.println("Metin");
        String metin= tarayici.nextLine();



        System.out.println("***********************");


        while(true) {
            System.out.println("Soru:");
            String a = tarayici.nextLine();

            if (a.contains("SONLANDIR")){
                break;
            }

            a=a.trim().replaceAll("( +)"," ");
            a=a.substring(0,a.length()-2);

            dizi.add(a);

        }
        for(String soru:dizi) {
            metin = metin.replaceAll("\\(d\\.", "(d ").replaceAll("ö\\.", "ö,").replaceAll("(\\d+)[. ]*\\/[ ]*(\\d+)\\.", "$1/$2").replaceAll("altmış", "atmış").replaceAll("[(]([a-zöğıçüş])\\.(\\d+)[)]", "($1 $2)");
            cevapla(normallestir(soru), simpleSentenceBoundaryDetector(metin));
        }*/


        paragrafVeSoruBul();




    }



    private static String kokal(String kelime){
        String kok="";

        TurkishMorphology morfoloji=TurkishMorphology.createWithDefaults();
        WordAnalysis sonuclar=morfoloji.analyze(kelime);

        for (SingleAnalysis sonuc : sonuclar) {
            kok=AnalysisFormatters.SURFACE_SEQUENCE.format(sonuc).split(" ")[0];
        }

        return kok;
    }



}

