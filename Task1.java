import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class Task1 {
	public static void main(String[] args) {
		List<Interval> list = readFile("3.in");
		int removeIndex = getRemoveIndex(list);
		list.remove(removeIndex);
		int result = merge(list);
		System.out.println(result);
	}
	
	
	public static List<Interval> readFile(String fileName) {
		List<Interval> list = new ArrayList<Interval>();
		
        File file = new File(fileName);  
        BufferedReader reader = null;  
        
        try {  
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            while ((tempString = reader.readLine()) != null) {  
            	if(tempString.contains(" ")){
            		String[] str = tempString.split(" ");
            		int s = Integer.parseInt(str[0]);
            		int e = Integer.parseInt(str[1]);
            		Interval interval = new Interval(s, e);
            		list.add(interval);
            	}
            }  
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
        
        return list;
    }  
	
	
	public static int getRemoveIndex(List<Interval> Intervals){
		int removeIndex = 0;
		int minRelutSum = Integer.MAX_VALUE;
		
		for (int i = 0; i < Intervals.size(); i++) {
			List<Interval> list = new ArrayList<Interval>();
			Interval intervalI = new Interval(Intervals.get(i).start,Intervals.get(i).end);
			list.add(intervalI);
			
			for (int j = 0; j < Intervals.size(); j++) {
				if(j != i){
					Interval intervalJ = Intervals.get(j);
					for (int k = 0; k < list.size(); k++) {
						Interval eachIntervalI = list.get(k);
						if((intervalJ.start<=eachIntervalI.start)&&(intervalJ.end>eachIntervalI.start)){
							eachIntervalI.start = intervalJ.end;
						}
						else if((intervalJ.start>eachIntervalI.start)&&(intervalJ.end<eachIntervalI.end)){
							Interval interval1 = new Interval(eachIntervalI.start,intervalJ.start);
							Interval interval2 = new Interval(intervalJ.end,eachIntervalI.end);
							list.remove(k);
							list.add(interval1);
							list.add(interval2);
						}
						else if((intervalJ.start<eachIntervalI.end)&&(intervalJ.end>=eachIntervalI.end)){
							eachIntervalI.end = intervalJ.start;
						}
					}
				}
			}
			
			int relutSum = 0;
			for (int j = 0; j < list.size(); j++) {
				relutSum = relutSum + (list.get(j).end - list.get(j).start);
			}
			
			if(relutSum<minRelutSum){
				minRelutSum = relutSum;
				removeIndex = i;
			}
		}
		
		return removeIndex;
	}
	
	
	private static class IntervalComparator implements Comparator<Interval> {
        @Override
        public int compare(Interval a, Interval b) {
            return a.start < b.start ? -1 : a.start == b.start ? 0 : 1;
        }
    }

	
    public static int merge(List<Interval> intervals) {
        Collections.sort(intervals, new IntervalComparator());

        LinkedList<Interval> merged = new LinkedList<Interval>();
        for (Interval interval : intervals) {
            if (merged.isEmpty() || merged.getLast().end < interval.start) {
                merged.add(interval);
            }
            else {
                merged.getLast().end = Math.max(merged.getLast().end, interval.end);
            }
        }
        
        int relutSum = 0;
        for (int i = 0; i < merged.size(); i++) {
        	relutSum = relutSum + (merged.get(i).end - merged.get(i).start);
		}
        
        return relutSum;
    }
	
	
	public static class Interval{
		int start;
		int end;
		Interval(int s, int e){
			start = s;
			end = e;
		}
	}
}
