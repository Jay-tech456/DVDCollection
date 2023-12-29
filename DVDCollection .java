package assig1;
import io.github.pixee.security.BoundedLineReader;
import java.io.*;
import java.util.Scanner;

public class DVDCollection {
	
	// Data fields
	
		/** The current number of DVDs in the array */
		private int numdvds;
		
		/** The array to contain the DVDs */
		private DVD[] dvdArray;
		
		/** The name of the data file that contains dvd data */
		private String sourceName;
		
		/** Boolean flag to indicate whether the DVD collection was
		    modified since it was last saved. */
		private boolean modified;
		
		/**
		 *  Constructs an empty directory as an array
		 *  with an initial capacity of 7. When we try to
		 *  insert into a full array, we will double the size of
		 *  the array first.
		 */
		public DVDCollection() {
			numdvds = 0;
			dvdArray = new DVD[7];
			modified = false; 								// By defult, Modified should be faulse 
		}
		
		public String toString() {
			
			// Return a string containing all the DVDs in the
			// order they are stored in the array along with
			// the values for numdvds and the length of the array.
			// See homework instructions for proper format.
			String entireString;
			entireString = "\nnumdvds = " + numdvds
							+ "\ndvdArray.length =" + dvdArray.length + "\n";
			for(int count = 0; count< numdvds; count++) { 
				entireString += "dvdArray[" + count + "] = " + dvdArray[count].toString();
				
			}
			
			System.out.println(entireString);

			



			return entireString;	// STUB: Remove this line.
		}

		public void addOrModifyDVD(String title, String rating, String runningTime) {
			// NOTE: Be careful. Running time is a string here
			// since the user might enter non-digits when prompted.
			// If the array is full and a new DVD needs to be added,
			// double the size of the array first.
			
			/*********************************************
			 * This entire function:
			 * 		Checks to see if the title exist and modifies it 
			 * 		Calls helper functions to alphabetize the entire list 
			 * 		adds new items in the dvd lists 
			 * 		double the size of the array if it exceeds the limit
			 * ********************************************/
			
			
			
			title = title.toUpperCase();
			int run = Integer.parseInt(runningTime); 										// converting runningTime into an iteger
			
			for(int count = 0; count < numdvds; count++ ) { 								// Best case O(1), Average Case O(1), Worst Case O(N)
				String dvd = dvdArray[count].getTitle(); 
				if(dvdArray[count].getTitle().contains(title)) { 							// An observer to see if the title exist within the 
					modified = true;														// sorted array
					dvdArray[count].setRating(rating);										// Changing the rating and the run time if the title exist within the list
					dvdArray[count].setRunningTime(run);

					toString();
					return;
				}	
					continue;
				
			}
			
			if(numdvds == dvdArray.length) {															// if the number of DVD exceeds the fixed sized array, then copy over the array into a temp array variable 
				DVD[] tempArray = new DVD[14];												// and expand dvdArray with double the index sizing
				for(int count = 0; count< numdvds; count++) { 
					tempArray[count] = new DVD(dvdArray[count].getTitle(), dvdArray[count].getRating(), dvdArray[count].getRunningTime());
				}
				dvdArray = tempArray; 
			}
			// *************** Only if the dvd does not already exist on the main list *****************************
			if(modified == false) { 														// if modified == false, it means there does not exist any dvd with the particular title
				
				dvdArray[numdvds] = new DVD(title, rating, run); 							// creating a new dvd into 
				numdvds++;
				
				
				
				while(!checkAlphabeticalOrder()) { 											// Similar to a Bubble sorting Algorithm, where it gets assistance from some helper functions 
																							// and will continue to run until the entire list is organized
					reOrganizeList();														// O(n^2 ) ( not the best sorting algorithm to use ) 
				}
				toString();
				return;
			}
			
				
			

		
		}
		
		public void removeDVD(String title) {
			title = title.toUpperCase(); 						// By default every single character is upper-cased before searching through the entire list 
			
			for(int count = 0; count < numdvds; count++) { 						//Possible O(n) if there does not exist any movies with that particullar title 
				System.out.println(dvdArray[count].getTitle());
				
				if(dvdArray[count].getTitle().contains(title)) { 				// observer to see if the title exist, a similar technique used in AddOrModify function
					
					//System.out.println(title + " found within the entire list "); 
					for(int i = count + 1; i< numdvds; i++) {					// override the current index location with all the dvd's that comes after it 	
																				// This helps to keep the entire collection sorted and searchable
						dvdArray[count].setTitle(dvdArray[i].getTitle());
						dvdArray[count].setRating(dvdArray[i].getRating());
						dvdArray[count].setRunningTime(dvdArray[i].getRunningTime());
						count++; 
						
					}
					numdvds -= 1;
					toString(); 
					//System.out.println("Successfully removed " + title + " from the entire list "); 
					return; 
				}
				
				}
			
			// System.out.print(title + " was not found within the entire collection of DVDS");
			
			return; 
				
			}

		
		public String getDVDsByRating(String rating) {
			
			rating = rating.toUpperCase(); 							// capitilizing the entire list
			String allDVDWithTheSameRating = "\n"; 
			
				for(int count = 0; count < numdvds; count++) { 
					
					if(dvdArray[count].getRating().contains(rating))  { 
						// System.out.println("This exist within the list " + dvdArray[count].getTitle());
						allDVDWithTheSameRating += dvdArray[count].getTitle() + " \n" ;				// building off the current string with all the movies with the same rating 
					}
					else { 
						continue; 
					} 
				}


			return 	allDVDWithTheSameRating; 

		}

		
		
		public int getTotalRunningTime() {
			int totalTime = 0; 
			for(int count = 0; count < numdvds; count++) { 								// O(n), only O(1) if the entire list is empty
				totalTime = totalTime + dvdArray[count].getRunningTime();
			}
			
			return totalTime;	// STUB: Remove this line.

		}

		
		public void loadData(String filename) {
			
			/* **********************************
			 * The purpose of this function is to:
			 * 		create a new file if the prompted file does not exist: 
			 * 				I added some defaults movies within the new list
			 * 				so the entire collection will not be empty ( you can't have 
			 * 				a collection if you dont have any dvds)  
			 * 		Load on the data, pass it into addOrModify function, and store it in the dvd collection
			 * 
			 * **********************************************/
			try {
					sourceName = filename;
					
					File f = new File(sourceName);
					if(f.createNewFile()) { 
						FileWriter myfile = new FileWriter(sourceName);				// the file is already  
						addOrModifyDVD("Star Wars", "PG-13", "433"); 				// create some defult movies within the list 
						addOrModifyDVD("Back to the Future", "PG-13", "120");
						addOrModifyDVD("17 Again", "PG-13", "103");
						myfile.close(); 
					} 
					else { 
					   BufferedReader myFile;
					   String[] info; 
				           myFile = new BufferedReader(new FileReader(filename));		// reads the text from the string of characters within the file
				           String file;
				           while ((file = BoundedLineReader.readLine(myFile, 5_000_000)) != null) {
				               info = file.split(",");									// splits the entire data into specific index position 
				               addOrModifyDVD(info[0], info[1], info[2]);				// for example "Back to the future" will be in index location 0
				           }															// "PG-13" will be in index location 1 (rating), and "433" will be in 
				           myFile.close();												// location 2 ( runntime)
					}
		           
		       } catch (IOException e) {
		           e.printStackTrace();
		       }
		}
		
		

				
		
		public void save() {

		try {
			Writer newFile = new FileWriter(sourceName, false);												// false is suppose to override the existing file
			for(int count = 0; count < numdvds; count++) { 
				newFile.write(dvdArray[count].getTitle() + ","+ dvdArray[count].getRating() + "," + dvdArray[count].getRunningTime() + System.getProperty("line.separator"));		// The System.getProperty("Line.Seperator") 
			}																																											// is equivelent to endl; in C++ when dealing with overriding a file
			
			newFile.close();
		} catch (IOException e) {
		
			e.printStackTrace();
		} 

		}
		
				
	//******************* THESE ARE HELPER FUNCTIONS TO CHECK AND SEE IF THE ENTIRE LIST IS IN ALPHABETICAL ORDER ***************************************

		public boolean checkAlphabeticalOrder() { 												// This is the helper boolean function that checks to see if the entire list 
						//																		// is initially in order
			/* *******************************************
			 *  The purpose of this function is being the observer of the entire collection, and 
			 *  seeing if everything is in the right order in the sequence of aplphabetical ordering
			 *  return false if it is not in the right order, return true if it is. 
			 *  
			 *  */
			int count = 0;	
			for(int j = count + 1; j < numdvds; j++)
				{ 
					Character first  =  dvdArray[count].getTitle().charAt(0);
					Character second = dvdArray[j].getTitle().charAt(0);
					if(first > second){
						//System.out.println("The entire list is not in the right order "); 
						return false;
					} else if (first == second) { 
						for(int i = 1; i< numdvds; i ++) { 
							first = dvdArray[count].getTitle().charAt(i);
							second = dvdArray[j].getTitle().charAt(i);
							if(first > second) { 
								//System.out.println("The entire list is not in order ");
								return false; 
							}
						}
						
					}
					else { 
						count++;
						continue; 
					}
				}
			
			//System.out.println("The entire list is in order ");

			return true;
		}
		
		public void reOrganizeList() {
			/***********************************
			 *  This is a helper function that reorganize the entire dvd in alphabetical order 
			 *  It only goes through the entire list, a similar approach to a bubble sort technique, but will continue 
			 *  to be called if checkAlphabeticalOrder() functions continue to return false 
			 *  
			 *  */
			
			int count = 0;
			String name; 															// These exist for "bookkeeping" the data will soon get overwritten by another location if it is not the same
			String rating; 
			int duration; 
			for(int i = count + 1; i < numdvds; i++) { 
				
				Character first = dvdArray[count].getTitle().charAt(0);
				Character second = dvdArray[i].getTitle().charAt(0);
				if(first > second) { 												// if the first 2
					name = dvdArray[count].getTitle(); 
					rating = dvdArray[count].getRating(); 
					duration = dvdArray[count].getRunningTime();
					
					dvdArray[count].setTitle(dvdArray[i].getTitle());
					dvdArray[count].setRating(dvdArray[i].getRating());
					dvdArray[count].setRunningTime(dvdArray[i].getRunningTime());
					
					dvdArray[i].setTitle(name);
					dvdArray[i].setRating(rating);
					dvdArray[i].setRunningTime(duration);
				}
				else if(first == second) { 												// If the first two characters are the same, then we need to compare most of the characters after the first one
					for(int j = 1; j < numdvds; j  ++) { 
						first = dvdArray[count].getTitle().charAt(i);
						second = dvdArray[i].getTitle().charAt(i);
						if(first > second) { 
							
							name = dvdArray[count].getTitle(); 							// Save the dava from 'this' position, and store it at the next index
							rating = dvdArray[count].getRating(); 
							duration = dvdArray[count].getRunningTime();
							
							dvdArray[count].setTitle(dvdArray[i].getTitle());
							dvdArray[count].setRating(dvdArray[i].getRating());
							dvdArray[count].setRunningTime(dvdArray[i].getRunningTime());
							
							dvdArray[i].setTitle(name);
							dvdArray[i].setRating(rating);
							dvdArray[i].setRunningTime(duration);
						}	
					}
					
				}
				
				count++;
				
			}
			
			return;
		}
}
