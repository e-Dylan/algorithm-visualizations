------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE:			Sorting App
PURPOSE OF PROJECT:		Build a sorting algorithm application for sorting algorithm visualization.
VERSION or DATE:		v 1.5
HOW TO START THIS PROJECT:
AUTHORS:			Dylan Smith
USER INSTRUCTIONS:

-------------------------------------------
---	Version Information		---
-------------------------------------------

 - Version 1.5

	[Implemented]:

		- Added additional QuickSort sorting algorithm with working full sort functionality.

		- Fixed all error checking for sorting function and animations:
			-> Can no longer call BubbleSort single pass, full pass, or full sort multiple times while any sort is already
			   in progress.

			-> Can on longer call QuickSort full sort while other sorting is in progress.

			-> Changing the sorting type now properly stops any sorting in progress and resets the array to the new sorting type.

			-> Changing the array size now stops any sorting in progress and resets the array properly.

			-> Generating a new array stops any sorting in progress.

			-> Can no longer shuffle the array while a sort is in progress.		


		- Updated and finished all Javadoc method/class documentation.
		
	[Added Extra Features]:

		- Dynamic css styles and classes for UI elements and recurring pleasing theme.

		- Color visualization for sorting algorithm states to display rectangles that are currently:
			-> Being evaluated		[BLUE] 
			-> need to be swapped		[RED]

		- Multiple sorting algorithms with working visualization.

		- Proper error checking for sorting functionality and array generation/size adjustments.
			-> Prevents errors occuring from making changes or trying to sort multiple times while sort is in progress.

		- Visualized adjustable array size and sorting speed sliders.

		- Animated visual array shuffling.



	[TO DO]:

		Priority:

			[Bubble Sort]

			✔ - Add the final FULL SORT functionality to continuously call bubble sort full pass keyframes until end condition.

			✔ - Implement bubble sort algorithm method:
				✔ -> Implement single-pass and full-pass sorting functionality.

			 - Implement colour visualization to highlight rectangles specific to their present state (sorting, sorted, evaluating, etc).
				✔Single Step:
					✔ - Default colors reset, evaluating rectangles are set blue in keyframes, and needing
					  evaluated rectangles are set red in keyframes. All Done.
				✔ Single Pass:
					✔ - Default colors reset, evaluating rectangles are set blue in keyframes, and needing
					  evaluated rectangles are set red in keyframes. All done.			

			✔ - Initialize array visualization rectangles in InstantiateArrayVisualization() method, called in initialize() on program start.
				✔ -> Calculate rectangle width and positioning given by array size (number of rectangles), position by distributing
				   properly across the visualization space.

			- Add additional colours to different states of sorting
				-> Purple for fully finished sorting state


			[Quick Sort]

			✔ - Add full sort functionality using quick sort method and keyframes.

				- Fix keyframes such that they generate individual keyframes for swaps of each rectangle in a timeline
				  in addition to the keyframes for full iterations of the recursive algorithm.
					-> Better visualizes the algorithm for each individual rectangle swap.

			- Add color visualization keyframes to quicksort.
	
		Regular:

			✔ - Add functionalities for changing and updating array size
			
			✔ - Connect sorting speed slider variable with sorting speed keyframe delay.

			