
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class GroupBy {
	private static final String INPUT_PATH = "input-groupBy/";
	private static final String OUTPUT_PATH = "output/groupBy-";
	private static final Logger LOG = Logger.getLogger(GroupBy.class.getName());

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n%6$s");

		try {
			FileHandler fh = new FileHandler("out.log");
			fh.setFormatter(new SimpleFormatter());
			LOG.addHandler(fh);
		} catch (SecurityException | IOException e) {
			System.exit(1);
		}
	}

	public static class Map extends Mapper<LongWritable, Text, Text, DoubleWritable> {
		private final static String emptyWords[] = { "" };
		private static int col_client = -1;
		private static int col_profit = -1;
		
		public void trouve_Col(String[] cols) {
			for(int i=0;i<cols.length;i++) {
				String[] cels = cols[i].split("\n");
				if(cels[0].equals("Customer ID")){
					col_client = i;
				} else if(cels[0].equals("Profit")){
					col_profit = i;
				}
			}
		}
		

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String line = value.toString();


			String[] cols = line.split(",");
			trouve_Col(cols); //Fonction permettant d'identifier les indices des colonnes qu'on souhaite groupBy

			// La ligne est vide : on s'arrête
			if (Arrays.equals(cols, emptyWords))
				return;
			
			double profit;
			
			try { //Pour palier à la première ligne contenant les titres des colonnes
				profit = Double.parseDouble(cols[col_profit]);
				context.write(new Text(cols[col_client]), new DoubleWritable(profit));
			} catch (NumberFormatException nfe) {} 
			
		}
	}

	public static class Reduce extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

		@Override
		public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
				throws IOException, InterruptedException {
			double sum = 0.0;

			for (DoubleWritable val : values)
				sum += val.get();

			//context.write(key, new IntWritable(sum));
			context.write(key, new DoubleWritable(sum));
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

		Job job = new Job(conf, "GroupBy");

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);

		job.setOutputValueClass(DoubleWritable.class); 

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
		FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH + Instant.now().getEpochSecond()));

		job.waitForCompletion(true);
	}
}
	
	