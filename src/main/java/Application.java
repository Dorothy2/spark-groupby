
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.network.protocol.Encoders;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.RelationalGroupedDataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StringType;
import org.apache.spark.sql.types.StructType;

import org.apache.spark.sql.functions.*;
/**
 * Research how Apache Spark handles group-by functionality
 * 
 * 
 * @author drifai
 * 
 * Expected server error if running on Windows 10
 * Does not affect functionality I am trying to test
 * 
 * 20/09/25 11:20:29 INFO SparkContext: Running Spark version 2.3.1
 * 20/09/25 11:20:30 WARN NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
 * 20/09/25 11:20:30 ERROR Shell: Failed to locate the winutils binary in the hadoop binary path
 * java.io.IOException: Could not locate executable null\bin\winutils.exe in the Hadoop binaries.
 *	at org.apache.hadoop.util.Shell.getQualifiedBinPath(Shell.java:378)
 *	at org.apache.hadoop.util.Shell.getWinUtilsPath(Shell.java:393)
 *	at org.apache.hadoop.util.Shell.<clinit>(Shell.java:386)
 *
 *
 */
public class Application {

	public static void main(String[] args) {

		SparkSession spark = SparkSession.builder().appName("documentation").master("local").getOrCreate();
		spark.sparkContext().setLogLevel("ERROR");

		// Here I am creating from POJO array
		// Input could also be Kafka stream
		Dataset<Row> test = spark
				.createDataFrame(Arrays.asList(new CashAvail("3C", "CASHTYPE1", "CASHSUBTYPE1", "US", 10000.00),
						new CashAvail("3C", "CASHTYPE1", "CASHSUBTYPE1", "US", 20000.00),
						new CashAvail("3C", "CASHTYPE2", "CASHSUBTYPE1", "EU", 20000.00),
						new CashAvail("3C", "CASHTYPE2", "CASHSUBTYPE2", "EU", 20000.00),
						new CashAvail("3D", "CASHTYPE1", "CASHSUBTYPE1", "US", 30000.00),
						new CashAvail("3D", "CASHTYPE2", "CASHSUBTYPE1", "EU", 40000.00)),
						CashAvail.class);
		
		System.out.println("Unaggregated ==>");
		test.show();
	
		Dataset<Row> df = test
		//GroupBy on multiple columns
		.groupBy("fundId", "cashType", "cashSubType", "currency")
		.sum("amount")
		.orderBy("fundId", "cashType", "cashSubType", "currency");

		System.out.println("Aggregated ==>");
		df.show();
				
		List<CashAvail> list = new ArrayList<>();
		
		df.collectAsList()
		  .forEach(e -> list.add( new CashAvail(e.getString(0), e.getString(1),
				  e.getString(2), e.getString(3), e.getDouble(4))));
		
		System.out.println("Back to POJOs  ==>");
		list.forEach(System.out::println);
	}

}
