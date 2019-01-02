import java.text.SimpleDateFormat;

public class test {
    public static void main(String[] args) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            System.out.println(sdf.parse("2018-12-01").getTime());
            System.out.println(System.currentTimeMillis());
        }catch (Exception e){

        }

        /*try {
            Date parse = sdf.parse("2018-12-21 16:20:57");
            Date parse2 = sdf.parse("2018-12-20 16:21:57");
            System.out.println((parse.getTime()-parse2.getTime())/(1000 * 60 * 60 * 24L));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

    }

   /* public static void main(String[] args) {
        List exportData = new ArrayList<Map>();
        Map row1 = new LinkedHashMap<String, String>();
        row1.put("1", "11");
        row1.put("2", "12");
        row1.put("3", "13");
        row1.put("4", "14");
        exportData.add(row1);
        row1 = new LinkedHashMap<String, String>();
        row1.put("1", "21");
        row1.put("2", "22");
        row1.put("3", "23");
        row1.put("4", "24");
        exportData.add(row1);

        //写入临时文件
        try {
            File tempFile =File.createTempFile("linshi",".csv");
            CsvWriter csvWriter=new CsvWriter(tempFile.getCanonicalPath(),',', Charset.forName("UTF-8"));
            String[] headers={"第一列名称","第二列名称","第三列名称","第四列名称"};
            csvWriter.writeRecord(headers);
        }catch (Exception e){
            e.printStackTrace();
        }

    }*/
}
