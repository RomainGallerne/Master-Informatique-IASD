import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

public class Join {
	private static final String INPUT_PATH = "input-join/";
	private static final String OUTPUT_PATH = "output/join-";
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

	public static class Map extends Mapper<LongWritable, Text, Text, Text> {
		private final static String emptyWords[] = { "" };
		private static int col_custkey = 0;
		private static int col_orderkey = 1;
		private static int col_custname = 1;
		private static int col_ordercomment = 8;

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			
			String line = value.toString();
			String[] cols = line.split("\\|");

			if (Arrays.equals(cols, emptyWords))
				return;
			
			String valeur;
			String cle;
			
			if(context.getInputSplit().toString().contains("orders.tbl")) {
				try {
					cle = cols[col_orderkey];
					valeur = cols[col_ordercomment];
					context.write(new Text(cle), new Text(valeur));
				} catch (NumberFormatException nfe) {}
			}
			
			else if (context.getInputSplit().toString().contains("customers.tbl")) {
				try {
					cle = cols[col_custkey];
					valeur = cols[col_custname];
					context.write(new Text(cle), new Text(valeur));
				} catch (NumberFormatException nfe) {}
			} 
			
			else {throw new InterruptedException("Fichier tbl non-identifié");}
		}
	}

	public static class Reduce extends Reducer<Text, Text, Text, Text> {
			private static ArrayList<String> valeursName;
			private static ArrayList<String> valeursComment;

		@Override
		public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
				
			Integer intKey = Integer.parseInt(key.toString());
			for (Text val : values) {
				String valString = val.toString();
				if(valeursName==null || valeursName.size() < intKey) {
					ArrayList<String> tempValName =  Arrays.copyOf(valeursName,intKey);;
					ArrayList<String> tempValComment = new ArrayList<>(intKey);
					tempValName.addAll(valeursName);
					tempValComment.addAll(valeursComment);
					valeursName = (ArrayList<String>)tempValName.clone();
					valeursComment = (ArrayList<String>)tempValComment.clone();
				} 
				if(valeursName.get(intKey) == null) {
					valeursComment.add(intKey,valString);
				}
				else {
					valeursName.add(intKey,valString);
				}
			}

			for(int i=0;i<valeursName.size();i++) {
				context.write(new Text(valeursName.get(i)), new Text(valeursComment.get(i)));
			}
			
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

		Job job = new Job(conf, "Join");

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);

		job.setOutputValueClass(Text.class); 

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
		FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH + Instant.now().getEpochSecond()));

		job.waitForCompletion(true);
	}
}
	
	