#!/usr/bin/env python
# This script allows a user to see the number of events a person 
#  attended and their average time at the events.
# Author: Cole Shaw
# Date:	  Jan 24, 2013
# Inputs: the filename / path for a csv file, formatted as:
# 	     <name>,<event time>,<event city>
# Outputs: number of events per person
#	   average time per event

import csv

def main():
	# define the variables we will use to sum up the different people
	#  for a small dataset, let's use a dict of dicts where format
	#  is {<name> : { <city> : hours } }
 	#  One could imagine for large datasets this would be slow, so
	#  perhaps maintaining cross-referenced lists is an alternative?
	data = dict()

	filename = raw_input("Enter the csv filename with full path: ")
	try:
		with open(filename, 'r') as datafile:
			rawdata = csv.reader(datafile, delimiter=',')
			for row in rawdata:
				name = row[0].lower()
				hours = int(row[1])
				city = row[2].lower()
				
				# if the person is already in the
				#  data dictionary, check if the
				#  city also exists. Add hours as
				#  appropriate.
				# if person doesn't exist, create them
				# note: not needed to keep disaggregated data, but
				#	 might be useful for some other analysis...
				if name in data.keys():
					data[name]['num_events'] += 1
					data[name]['tot_hours'] += hours
					if city in data[name].keys():
						data[name][city] += hours
					else:
						data[name][city] = hours
				else:
					data[name] = dict()
					data[name][city] = hours
					data[name]['num_events'] = 1
					data[name]['tot_hours'] = hours
		# now create the report
		print "Report for " + filename + "\n"
		print "==========\n"
		print "Line\tPerson\tEvents\tAverage Hours\n"
		line = 1
		for person in data:
			num_events = data[person]['num_events']
			avg_hours = data[person]['tot_hours'] / float(num_events)
			print str(line) + "\t" + person + "\t" + str(num_events) + "\t" + str(avg_hours) + "\n"
			line += 1
		print "==========\n" 
	except IOError as e:
		print "Error opening file. Please try again."

#-------------------------------
if __name__ == "__main__":
    main()
