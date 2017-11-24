package com.stq.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.hankcs.hanlp.HanLP;
import com.sun.el.parser.ParseException;

import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.ss.usermodel.WorkbookFactory;  
import org.apache.poi.ss.usermodel.DateUtil;  


@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TeTest extends SpringTransactionalTestCase{
	
	@Value("#{envProps.server_url}")
	private String excelUrl;
	
    @Autowired  
    @Qualifier("inputToKafka_weibo")  
    MessageChannel messageChannel;  

    /**
    @Autowired  
    @Qualifier("inputFromKafka_weibo")  
    PollableChannel pollableChannel;  
     */
	private static final Logger log = LoggerFactory.getLogger(TeTest.class);
	
	//@Test
	public void test0() throws IOException, ParseException {	
		System.out.println("11111");
		//System.out.println(new SpringHttpClient().getMethodStr("http://private-9394a-g22.apiary-mock.com/timeframe/newuser"));
/*		System.out.println(new SpringHttpClient().getMethodStr(excelUrl));
		List<ConfigServer> beanList = binder.getMapper().readValue(new SpringHttpClient().getMethodStr(excelUrl), new TypeReference<List<ConfigServer>>() {}); 
		for (ConfigServer configServer : beanList) {
			System.out.println("2222  "  + configServer.getServerName());
		}*/
	}
	
	//@Test
	public void test1() throws IOException, ParseException {	
        for (int i = 0; i < 15; i++) {  
            Message<String> message = new GenericMessage<String>("test-------------" + (i + 100));  
            boolean flag = messageChannel.send(message);  
            
  
            System.out.println(flag + "=============" + (i + 100));  
        }  
/*        Message<?> received = pollableChannel.receive(10000);  
        while (received != null) {  
            System.out.println("|||" + received);  
            received = pollableChannel.receive(10000);  
        } */
	}
	
	
	
	@SuppressWarnings("resource")
	//@Test
	public void test2() throws IOException, ParseException {	
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch")
                .put("xpack.security.transport.ssl.enabled", false)
                .put("xpack.security.user", "elastic:changeme")
                .put("client.transport.sniff", true).build();
		try {
			TransportClient client = new PreBuiltXPackTransportClient(settings)
			        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
			SearchResponse result = client.prepareSearch("twitter").setTypes("tweet")
			        .setQuery(
			        		QueryBuilders.boolQuery()
			        		.must( QueryBuilders.termsQuery("user", "kimchy"))
			        ).execute().actionGet();
			
			log.info("1111  " + result);
			client.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
		
	//@Test
	public void test3() throws IOException, ParseException {	
/*		RestClient restClient = RestClient.builder(
		        new HttpHost("localhost", 9200, "http"),
		        new HttpHost("localhost", 9201, "http")).build();
		restClient.close();*/
		
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials("elastic", "changeme"));

		RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200))
		        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
		            @Override
		            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
		                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
		            }
		        }).build();
		
		
		restClient.close();
	}
	
	//@Test
	public void test4() throws IOException, ParseException {	
		 //String fileName = "/Users/admin/Desktop/word/dataset_602123/中文情感挖掘酒店评论语料/ChnSentiCorp_htl_ba_2000/neg/neg.999.txt"; 
		 //String fileName = "/Users/admin/Desktop/word/news_fasttext_train.txt";
		 //readFileByLines(fileName);
		 //readFileByChars(fileName);
		 for (int i = 600; i < 3000; i++) {
			 System.out.println("文本neg："+i);
			 readFileByLines("/Users/admin/Desktop/word/dataset_602123/中文情感挖掘酒店评论语料/ChnSentiCorp_htl_unba_10000/neg/neg."+i+".txt","差评");
		 }
		 
		 for (int i = 1400; i < 7000; i++) {
			 System.out.println("文本pos："+i);
			 readFileByLines("/Users/admin/Desktop/word/dataset_602123/中文情感挖掘酒店评论语料/ChnSentiCorp_htl_unba_10000/pos/pos."+i+".txt","好评");
		 }
		 
/*		 
		 for (int i = 0; i < 600; i++) {
			 System.out.println("文本neg："+i);
			 readFileByLines("/Users/admin/Desktop/word/dataset_602123/中文情感挖掘酒店评论语料/ChnSentiCorp_htl_unba_10000/neg/neg."+i+".txt","差评");
		 }
		 
		 for (int i = 0; i < 1400; i++) {
			 System.out.println("文本pos："+i);
			 readFileByLines("/Users/admin/Desktop/word/dataset_602123/中文情感挖掘酒店评论语料/ChnSentiCorp_htl_unba_10000/pos/pos."+i+".txt","好评");
		 }*/
	}

	//@Test
	public void test5() throws IOException, ParseException {	
		String fileName = "/Users/admin/Desktop/word/train_corpus/train/";
		File file = new File(fileName);
		String[] t = file.list();
		int num = 0;
		for (String fName : t) {
			if(!fName.equals(".DS_Store")){
				String fileName2 = "/Users/admin/Desktop/word/train_corpus/train/"+fName;
				File file2 = new File(fileName2);
				String[] t2 = file2.list();
				if(!fName.equals(".DS_Store")){
					System.out.println(fName + "  " + t2.length);
					num = num + t2.length;
					for (String string : t2) {
						if(!string.equals(".DS_Store")){
							readFileByLinesStringBuffer("/Users/admin/Desktop/word/train_corpus/train/"+fName+"/"+string,fName);
						}
					}
						
				}
			}
		}
		System.out.println("train 总数：" + num);
	}
	
	//@Test
	public void test6() throws IOException, ParseException {	
		String fileName = "/Users/admin/Desktop/word/answer/";
		File file = new File(fileName);
		String[] t = file.list();
		int num = 0;
		for (String fName : t) {
			if(!fName.equals(".DS_Store")){
				String fileName2 = "/Users/admin/Desktop/word/answer/"+fName;
				File file2 = new File(fileName2);
				String[] t2 = file2.list();
				if(!fName.equals(".DS_Store")){
					System.out.println(fName + "  " + t2.length);
					num = num + t2.length;
					for (String string : t2) {
						if(!string.equals(".DS_Store")){
							readFileByLinesStringBuffer("/Users/admin/Desktop/word/answer/"+fName+"/"+string,fName);
						}
					}
						
				}
			}
		}
		System.out.println("test 总数：" + num);
	}
	
	//@Test
	public void test7() throws IOException, ParseException {	
        File file = new File("/Users/admin/Desktop/word/646864264/neg.xls");  
        BufferedReader reader = null;  
        try {  
            //System.out.println("以行为单位读取文件内容，一次读一整行："); 
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
            reader = new BufferedReader(isr);  
            String tempString = null;  
            int line = 1;  
            StringBuffer s = new StringBuffer();
            // 一次读入一行，直到读入null为文件结束  
            while ((tempString = reader.readLine()) != null) {  
                // 显示行号  
            	if(!tempString.equals("")){
                    s.append(tempString);
                    line++;  
            	}
            }  
            System.out.println("line "  + ": " + s);  
            //写文件
            writeFile("/Users/admin/Desktop/word/neg.txt", "__label__负面" + s+ "\r\n");
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
	}
	
	/**
	 * 随机数
	 * @param size
	 * @return
	 */
	public static Set<Integer> generateRandomArray(int size,int range) {
		Set<Integer> set = new LinkedHashSet<Integer>(); // 集合是没有重复的值,LinkedHashSet是有顺序不重复集合,HashSet则为无顺序不重复集合
		Integer num = size;
		Random ran = new Random();
		while (set.size() < num) {
			Integer tmp = ran.nextInt(range); // 0-51之间随机选一个数
			set.add(tmp);// 直接加入，当有重复值时，不会往里加入，直到set的长度为52才结束
		}
		return set;
	}
	
	/**
	 * 读取excel
	 * @throws IOException
	 * @throws ParseException
	 */
	//@Test
	public void test8() throws IOException, ParseException {
/*		Set<Integer> set = generateRandomArray(100,1600);
		int i = 1;
		for (Integer integer : set) {
			System.out.println(i+"："  + integer.intValue());
			i++;
		}*/
		readExcel2();
	}
	
	/**
	 * 去除文本换行符
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	public void readExcel() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// 同时支持Excel 2003、2007
			File excelFile = new File("/Users/admin/Desktop/word/1月微博分类文本1600条.xlsx"); // 创建文件对象
			FileInputStream is = new FileInputStream(excelFile); // 文件流
			Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel
															// 2003/2007/2010
															// 都是可以处理的
			int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
			Set<Integer> set = generateRandomArray(100,1600);
			// 遍历每个Sheet
			for (int s = 0; s < sheetCount; s++) {
				Sheet sheet = workbook.getSheetAt(s);
				int rowCount = sheet.getPhysicalNumberOfRows(); // 获取总行数
				// 遍历每一行
				
				for (int r = 1; r < rowCount; r++) {
					Row row = sheet.getRow(r);
					int cellCount = row.getPhysicalNumberOfCells(); // 获取总列数
					// 遍历每一列
					StringBuffer sbu = new StringBuffer();
					boolean bo = true;
					for (int c = 0; c < cellCount; c++) {
						if(c==2||c==3||c==11 && bo == true){
							Cell cell = row.getCell(c);
							if(cell!=null){
								int cellType = cell.getCellType();
								String cellValue = null;
								switch (cellType) {
								case Cell.CELL_TYPE_STRING: // 文本
									cellValue = cell.getStringCellValue();
									break;
								case Cell.CELL_TYPE_NUMERIC: // 数字、日期
									if (DateUtil.isCellDateFormatted(cell)) {
										cellValue = fmt.format(cell.getDateCellValue()); // 日期型
									} else {
										cellValue = String.valueOf(cell.getNumericCellValue()); // 数字
									}
									break;
								case Cell.CELL_TYPE_BOOLEAN: // 布尔型
									cellValue = String.valueOf(cell.getBooleanCellValue());
									break;
								case Cell.CELL_TYPE_BLANK: // 空白
									cellValue = cell.getStringCellValue();
									break;
								case Cell.CELL_TYPE_ERROR: // 错误
									cellValue = "错误";
									break;
								case Cell.CELL_TYPE_FORMULA: // 公式
									cellValue = "错误";
									break;
								default:
									cellValue = "错误";
								}
								if(c==2){
									//只留标签是新闻内容的,新闻内容 营销内容 用户主动提及 求助上访 行业文章 其他
									if(!cellValue.equals("其他")){
										bo = false;
										break;
									}
									//sbu.append(" __label__"+cellValue);
								}
								else if(c==3){
									sbu.append(" __label__"+cellValue);
								}
								else if(c==11){
									sbu.append(" "+ String.join(" ", HanLP.extractSummary(replaceBlank(cellValue), 5)));
									sbu.append(" "+ String.join(" ", HanLP.extractKeyword(replaceBlank(cellValue), 35)));
									sbu.append(" "+ String.join(" ", HanLP.extractPhrase(replaceBlank(cellValue), 35)));
								}
							}
						}
					}
		            //写文件
					if(set.contains(r) && bo== true){
						 writeFile("/Users/admin/Desktop/word/weibo2_6.txt",  sbu + "\r\n");
					}else if(bo == true){
						 writeFile("/Users/admin/Desktop/word/weibo1_6.txt",  sbu + "\r\n");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 主分类
	 */
	public void readExcel2() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// 同时支持Excel 2003、2007
			File excelFile = new File("/Users/admin/Desktop/word/1月微博分类文本1600条.xlsx"); // 创建文件对象
			FileInputStream is = new FileInputStream(excelFile); // 文件流
			Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel
															// 2003/2007/2010
															// 都是可以处理的
			int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
			Set<Integer> set = generateRandomArray(100,1600);
			// 遍历每个Sheet
			for (int s = 0; s < sheetCount; s++) {
				Sheet sheet = workbook.getSheetAt(s);
				int rowCount = sheet.getPhysicalNumberOfRows(); // 获取总行数
				// 遍历每一行
				
				for (int r = 1; r < rowCount; r++) {
					Row row = sheet.getRow(r);
					int cellCount = row.getPhysicalNumberOfCells(); // 获取总列数
					// 遍历每一列
					StringBuffer sbu = new StringBuffer();
					for (int c = 0; c < cellCount; c++) {
						if(c==2||c==3||c==11){
							Cell cell = row.getCell(c);
							if(cell!=null){
								int cellType = cell.getCellType();
								String cellValue = null;
								switch (cellType) {
								case Cell.CELL_TYPE_STRING: // 文本
									cellValue = cell.getStringCellValue();
									break;
								case Cell.CELL_TYPE_NUMERIC: // 数字、日期
									if (DateUtil.isCellDateFormatted(cell)) {
										cellValue = fmt.format(cell.getDateCellValue()); // 日期型
									} else {
										cellValue = String.valueOf(cell.getNumericCellValue()); // 数字
									}
									break;
								case Cell.CELL_TYPE_BOOLEAN: // 布尔型
									cellValue = String.valueOf(cell.getBooleanCellValue());
									break;
								case Cell.CELL_TYPE_BLANK: // 空白
									cellValue = cell.getStringCellValue();
									break;
								case Cell.CELL_TYPE_ERROR: // 错误
									cellValue = "错误";
									break;
								case Cell.CELL_TYPE_FORMULA: // 公式
									cellValue = "错误";
									break;
								default:
									cellValue = "错误";
								}
								if(c==2){
									sbu.append(" __label__"+cellValue);
/*									sbu.append(" __"+r+"__");*/
								}
/*								else if(c==3){
									sbu.append(" __label__"+cellValue);
								}*/
								else if(c==11){
									sbu.append(" "+ String.join(" ", HanLP.extractSummary(replaceBlank(cellValue), 5)));
									sbu.append(" "+ String.join(" ", HanLP.extractPhrase(replaceBlank(cellValue), 35)));
									sbu.append(" "+ String.join(" ", HanLP.extractKeyword(replaceBlank(cellValue), 35)));
								}
							}
						}
					}
					
		            //写文件
					if(set.contains(r)){
						 writeFile("/Users/admin/Desktop/word/weibo2.txt",  sbu + "\r\n");
					}else{
						 writeFile("/Users/admin/Desktop/word/weibo1.txt",  sbu + "\r\n");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * a100
	 * @throws IOException
	 * @throws ParseException
	 */
	//@Test
	public void test9() throws IOException, ParseException {	
        File file = new File("/Users/admin/Desktop/word/data/training.csv");  
        BufferedReader reader = null;  
        try {  
            //System.out.println("以行为单位读取文件内容，一次读一整行："); 
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
            reader = new BufferedReader(isr);  
            String tempString = null;  
            int line = 1;  
            
            // 一次读入一行，直到读入null为文件结束  
            while ((tempString = reader.readLine()) != null) {  
                // 显示行号  
            	StringBuffer sbu = new StringBuffer();
            	if(!tempString.equals("")){
                    sbu.append(" __label__"+tempString.split(",")[0]);
					sbu.append(" "+ String.join(" ", HanLP.extractKeyword(replaceBlank(tempString.split(",")[1]), 30)));
					sbu.append(" "+ String.join(" ", HanLP.extractPhrase(replaceBlank(tempString.split(",")[1]), 30)));
                    line++;  
            	}
                //写文件
            	writeFile("/Users/admin/Desktop/word/a1001.txt",  sbu+ "\r\n");
            }  
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
	}
	
    /** 
     * StringBuffer
     * 以行为单位读取文件，常用于读面向行的格式化文件 
     */  
    public static void readFileByLinesStringBuffer(String fileName,String status) {  
        File file = new File(fileName);  
        BufferedReader reader = null;  
        try {  
            //System.out.println("以行为单位读取文件内容，一次读一整行："); 
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "gbk");
            reader = new BufferedReader(isr);  
            String tempString = null;  
            int line = 1;  
            StringBuffer s = new StringBuffer();
            // 一次读入一行，直到读入null为文件结束  
            while ((tempString = reader.readLine()) != null) {  
                // 显示行号  
            	if(!tempString.equals("")){
                    s.append(tempString);
                    line++;  
            	}
            }  
            System.out.println("line " + status + ": " + s);  
            //写文件
            writeFile("/Users/admin/Desktop/word/train.txt", "__label__" + status + " " + s+ "\r\n");
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
    }
	
    /** 
     * 以行为单位读取文件，常用于读面向行的格式化文件 
     */  
    public static void readFileByLines(String fileName,String status) {  
        File file = new File(fileName);  
        BufferedReader reader = null;  
        try {  
            //System.out.println("以行为单位读取文件内容，一次读一整行："); 
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "gbk");
            reader = new BufferedReader(isr);  
            String tempString = null;  
            int line = 1;  
            // 一次读入一行，直到读入null为文件结束  
            while ((tempString = reader.readLine()) != null) {  
                // 显示行号  
            	if(!tempString.equals("")){
                    System.out.println("line " + line + ": " + tempString);  
                    //写文件
                    writeFile("/Users/admin/Desktop/word/dataset_602123/中文情感挖掘酒店评论语料/ChnSentiCorp_htl_unba_10000/train.txt", "__label__" + status + " " + tempString+ "\r\n");
                    line++;  
            	}
            }  
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
    }      
    
    /**
     * B方法追加文件：使用FileWriter
     */
    public static void writeFile(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
