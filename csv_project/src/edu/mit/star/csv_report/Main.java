package edu.mit.star.csv_report;

import java.awt.Container;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.event.*;
import java.util.*;

public class Main extends JFrame
{
	private static final long serialVersionUID = 1L;
	JTextArea input;
	JButton calculate;
	JPanel report;
	JTextArea output;

	@Override
	public void addNotify()
	{
		super.addNotify();
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.PAGE_AXIS));
		input = new JTextArea(12, 40);
		input.setBorder(BorderFactory.createTitledBorder("Input"));
		output = new JTextArea(12, 40);
		calculate = new JButton("Calculate");
		calculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// read the data from the input section
				String[] user_data = input.getText().split("\n");
				Set<String> data_names = new HashSet<String>();

				// use the names as keys for these HashMaps
				HashMap<String, Integer> data_events = new HashMap<String, Integer>();
				HashMap<String, Float> data_hours = new HashMap<String, Float>();
				HashMap<String, Float> avg_hours = new HashMap<String, Float>();
				
				for( int i = 0; i < user_data.length-1; i++) {
					String[] row = user_data[i].split(",");
					String name = row[0].toLowerCase();
					float hours = Float.parseFloat(row[1]);
					
					// check if names exists in set user_data...if not, add it
					if (data_names.contains(name)) {
						data_events.put(name, data_events.get(name) + 1);
						data_hours.put(name, data_hours.get(name) + hours);
					} else {
						data_names.add(name);
						data_events.put(name, new Integer(1));
						data_hours.put(name, hours);
					}
					avg_hours.put(name, data_hours.get(name) / data_events.get(name));
				}
				
				// output text to Report area
				Iterator<String> name_iterator = data_names.iterator();
				output.setText("Line\tName\tEvents\tAvg Hours\n");
				int line = new Integer(1);
				String text = new String();
				String name = new String();
				int events = new Integer(0);
				float hours = new Float(0);
				while(name_iterator.hasNext()) {
					name = name_iterator.next();
					events = data_events.get(name);
					hours = avg_hours.get(name);
					text = line + "\t" + name + "\t" + events + "\t" + hours + "\n";
					line++;
					output.append(text);
				}
			}
		});
		report = new JPanel();
		report.setBorder(BorderFactory.createTitledBorder("Report"));
		c.add(input);
		c.add(calculate);
		c.add(report);
		c.add(output);
	}

	public void actionPerformed(ActionEvent a) {

	}
	
	public static void main(String[] args)
	{
		try
		{
			SwingUtilities.invokeAndWait(new Runnable()
			{

				@Override
				public void run()
				{
					Main m = (new Main());
					m.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					m.pack();
					m.setVisible(true);
				}
			});
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}
}
