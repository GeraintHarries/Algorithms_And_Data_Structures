/**
*
*	Geraint Harries
*	_______________
*	1100682
*	_______
*
*	
*	I haven't got the transposeSequence working. 
*	I roughly understood the logic to it
*		
*		- decrement through CumulativeArray
*			- Iterate through sequence
*				- If the cumulativearray(i) and sequence(index) are the same
*					- Calculate the new index and add the element to the end of the sequence
*					- Delete 'old' sequence entry
*	
*	However, I didn't (and still don't) know how to implement it.
*	After a long stuggle with the above logic, I eventually used the swap technique (swapping x and y)
*	I know this doesn't return all values as the array is out of order, however I figured a try was better
*	not trying at all.
*
**/


import java.lang.*;
import java.util.*;
import java.util.Collections;

public class Sparse{

	static int[] 				arrayInSequence;
	static int[]				arrayInSequence2;	
	static int[]				frequency;	
	static ArrayList<int[]> 	sequence 			= new ArrayList<int[]>();
	static int 					size,
								seed,
								threshold;
	static int[][] 				theArray;
	static ArrayStack 			s 					= new ArrayStack();
	static ArrayStack			sr 					= new ArrayStack();
	static ArrayList<Integer> 	tempStack 			= new ArrayList<Integer>();

	public static void main(String [] args){
		
		// Arguments
			 	seed 			= Integer.parseInt(args[0]);
			 	threshold 		= Integer.parseInt(args[1]);
				size 			= Integer.parseInt(args[2]);
				theArray 		= new int[size][size];

		// Print 2D non-sparse
		System.out.println("initial 2D non-sparse matrix:");
		initialiseArray();
		Print2D();

		// Print 2D sparse and create sequence
		System.out.println("initial 2D sparse matrix:");		
		createSparse();
		Print2D();
		printSequence();
		s.print();
		System.out.println("");

		// Given input
		reset(4, 0, 99);
		PrintFullMatrix();
		reset(2,0,35);
		reset(2,1,77);
		undo();
		PrintFullMatrix();
		reset(2,1,0);
		reset(0,2,22);
		undo();
		reset(5,2,55);
		reset(3,3,63);
		undo();
		PrintFullMatrix();
		transposeSequence();
		PrintFullMatrix();

	}

	/**
	*
	*	Cumulative
	*	__________
	*
	*
	*	This method determines the cumulative frequency of the matrix.
	*	It increments through the sequence and determines the column of each 
	*	value (by converting the index into a y value). It then increments an array
	*	with the frequencies in
	*
	*	The array is then cumulatively added by using a temp variable. Temp adds the 
	*	current and new value together and then saves it to frequency. This causes frequency values
	*	to accumulate
	*
	**/
	public static void cumulative(){
		System.out.print("cumulative column sizes:");

		int 	x,
				y,
				index,
				temp 			=0; 
				frequency 		= new int[size];

		for(int i = 0; i < sequence.size(); i++){

			y = (sequence.get(i)[0] % size);
			frequency[y] = frequency[y] + 1;

		}

		for(int i = 0; i < frequency.length; i++){
			temp = temp + frequency[i];
			frequency[i] = temp;
			System.out.print(frequency[i] + " ");
		}
		System.out.println("");
	}

	/**
	*
	*	Transpose Sequence
	*	__________________
	*
	*	// REWRITE
	*	
	*	Although not in the specified way, this method determines the transposed array.
	*	It determines the (x,y) values from the index and swaps them when creating the new index.
	*	x becomes y, y becomes x. e.g. [2,1] => [1,2].
	*	Once changing the values in the sequence, it then resets it using the resetNoOutput function
	*	The stack is then emptied, sequence and stack printed. 
	*	
	*	The whole sequence isn't printed as the elements aren't in order.
	*
	**/
	public static void transposeSequence(){

		System.out.println("transposing");

		cumulative();

		int 	y,
				x,
				index,
				count	= 0;

		for(int i = 0; i < sequence.size(); i++){
					
			y = sequence.get(i)[0] % size;
			x = (sequence.get(i)[0] / size);
			index = (y*size)+x;
			
			int[] arrayInSequence2 = {index, sequence.get(i)[1]};
			sequence.set(i, arrayInSequence2);
		}

		while(s.size() > 0){

			s.pop();
		}

		printSequence();
		s.print();
		System.out.println("");

	}

	/**
	*
	*	Undo
	*	____
	*	
	*	
	*	This method returns the last pair in the stack back into the sequence, essentially
	*	undoing the last reset.
	*	It determines the (x,y) co-ordinates from the index and writes them (as per formatting).
	*	It iterates through the sequence, comparing the 
	*
	**/
	public static void undo(){

		int 	value = (Integer)s.pop(),
				index = (Integer)s.pop(),
				x = index % size,
				y = (index / size);
		
		System.out.println("undo (" + x +"," + y + ") to " + value );

		for(int i = 0; i < sequence.size(); i++){
			if(sequence.get(i)[0] == index ){

				int[] arrayInSequence = {index, value};
				sequence.set(i, arrayInSequence);

				if(sequence.get(i)[1] == 0){

					sequence.remove(i);

				}
			}
		}

		printSequence();
		s.print();
		System.out.println("");

	}

	/**
	*
	*	Initialise Array
	*	________________
	*
	*
	*	This method creates a 2D array filled with random values from 0 to 100.
	*
	**/
	public static void initialiseArray(){
		
		Random rand = new Random(seed);
		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){

				theArray[i][j] = rand.nextInt(100);

			}
		}
	}

	/**
	*
	*	Print Sequence
	*	______________
	*	
	*
	*	This method prints the sequence in order by iterating through the sequence and outputting 
	*	the two values in the current index.
	*
	**/
	public static void printSequence(){
		
		System.out.print("sequence: ");

		for(int i = 0; i < sequence.size(); i++){
			
			System.out.print(sequence.get(i)[0] + " " + sequence.get(i)[1] + "; ");
		
		}

		System.out.println("");
	
	}

	/**
	*
	*	Create Sparse
	*	_____________
	*
	*
	*	This method converts the array into a sparse array.
	*	It iterates through the array and if the value is greater than the given threshold,
	*	it changes the value to 0. If the value is less than the given threshold, it adds the
	*	index (of the value) and value to a position in the index (of sequence).
	*
	**/
	public static void createSparse(){

		int count = 0;

		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				if(theArray[i][j] > threshold){

					theArray[i][j] = 0;

				}else{

					int[] arrayInSequence = {count, theArray[i][j]};
					sequence.add(arrayInSequence);

				}

				count += 1;

			}
		}
	}

	/**
	*
	*	Reset No Output
	*	_______________
	*
	*
	*	Does exactly what Reset does but doesn't output the stack or sequence at the end.
	*
	**/
	public static void resetNoOutput(int x, int y, int value){

		int 			index = (y*size)+x,
						count = 0;
		boolean 		isFound = false;

		for(int i = 0; i < sequence.size(); i++){
			if(isFound != true){
					if(index == sequence.get(i)[0]){

						isFound = true;
						int[] arrayInSequence = {index ,value};
						s.push(index);
						s.push(sequence.get(i)[1]);
						sequence.set(i, arrayInSequence);
						
						if(sequence.get(i)[1] == 0){

							sequence.remove(i);
						}

					}else if(index < sequence.get(0)[0]){

						int[] arrayInSequence = {index, value};
						s.push(index);
						s.push((Integer)0);
						sequence.add(0, arrayInSequence);
						
						if(sequence.get(i)[1] == 0){

							sequence.remove(0);

						}

					}else if(index > sequence.get(i)[0] && index < sequence.get(i+1)[0]){

						int[] arrayInSequence = {index, value};
						s.push(index);
						s.push((Integer)0);
						sequence.add(i + 1, arrayInSequence);
						
						if(sequence.get(i)[1] == 0){

							sequence.remove(i + 1);

						}

						break;

					}else if(index > sequence.get(sequence.size()-1)[0]){

						int[] arrayInSequence = {index, value};
						s.push(index);
						s.push((Integer)0);
						sequence.add(arrayInSequence);
						
						if(sequence.get(i)[1] == 0){

							sequence.remove(sequence.size());

						}

					}

				count += 1;

			}	
		}
	}

	/**
	*
	*	Reset
	*	_____
	*
	*
	*	This method adds or resets a value in sequence given the three input x, y and value. 
	*	It finds the index of the value by using the (x,y) co-ordinates and sets isfound (the 
	*	flag to say if a value has been found) to false.
	*	The method iterates through the sequence, on the condition isFound is false. isFalse is 
	*	a flag which says whether a direct match has been found.
	*
	*	If the index (found from (x,y)) equals the index (position) of the value in sequence, it 
	*	sets isFound to true, adds the the original value to the stack and new value to thesequence. 
	*	It then checks that the new value doesn't equal 0. If so, it removes it.
	*
	*	If the index (found from (x,y)) is less than the first index (position) of the value in the 
	*	sequence, it adds the index and original value (0) to the stack and new value into sequence.
	*
	*	If the index (found from (x,y)) is between two indexes (positions) of the values in the 
	*	sequence, it adds the index and original value (0) to the stack and the new value to the sequence. 
	*
	*	If the index (found from (x,y)) is greater than the last index (position) of the value in the 
	*	sequence, it adds the index and origina value (0) to the stack and the new value to the sequence.
	*
	*	It then outputs the sequence and stack.
	*
	**/
	public static void reset(int x, int y, int value){

		System.out.println("reset (" + x + "," + y + ") to " + value);

		int 			index = (y*size)+x,
						count = 0;
		boolean 		isFound = false;

		for(int i = 0; i < sequence.size(); i++){
			if(isFound != true){
				if(index == sequence.get(i)[0]){

					isFound = true;
					int[] arrayInSequence = {index ,value};
					s.push(index);
					s.push(sequence.get(i)[1]);
					sequence.set(i, arrayInSequence);
						
					if(sequence.get(i)[1] == 0){

						sequence.remove(i);

					}

				}else if(index < sequence.get(0)[0]){

					int[] arrayInSequence = {index, value};
					s.push(index);
					s.push((Integer)0);
					sequence.add(0, arrayInSequence);

				}else if(index > sequence.get(i)[0] && index < sequence.get(i+1)[0]){

					int[] arrayInSequence = {index, value};
					s.push(index);
					s.push((Integer)0);
					sequence.add(i + 1, arrayInSequence);

					break;

				}else if(index > sequence.get(sequence.size()-1)[0]){

					int[] arrayInSequence = {index, value};
					s.push(index);
					s.push((Integer)0);
					sequence.add(arrayInSequence);

				}
			}	
		}

		printSequence();
		s.print();
		System.out.println("");

	}

	/**
	*
	*	Print Full Matrix
	*	_________________
	*
	*
	*	This mmethod prints a 2D array from a the sequence.
	*	The method interates through a count. If the count is equal to the sequence index it
	*	writes the value. If the count is not equal to the sequence index (i.e. it's not there) 
	*   it writes 0.
	*
	**/
	public static void PrintFullMatrix(){

		System.out.println("reconstructed 2D sparse matrix:");

		int 		count 		= 0,
					position 	= 0; 

		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				if(position < sequence.size()){
					if(count == sequence.get(position)[0]){

						System.out.print("\t" + sequence.get(position)[1]);
						position += 1; 
						count += 1;

					}else{

						System.out.print("\t0");
						count += 1;

					}

				}else{

					System.out.println("\t0");

				}
			}

			System.out.println();

		}
	}

	/**
	*
	*	Print 2D
	*	________
	*
	*
	*	The method iterates through the (x,y) values of the array and prints
	*	the corresponding value
	*
	**/
	public static void Print2D(){
		for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){

                System.out.print("\t" + theArray[i][j] + " ");

            }

        	System.out.println();	

		}

		System.out.println("");

	}
}