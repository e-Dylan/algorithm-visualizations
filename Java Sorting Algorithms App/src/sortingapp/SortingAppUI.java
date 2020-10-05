package sortingapp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Random;


/**
 * Sorting app UI class contains all functionality and logic for the sorting algorithm app,
 * including UI initialization and UI element interaction.
 *
 * @author Dylan Smith
 * @version v1.5
 */
public class SortingAppUI {

    private Random random = new Random();
    public String sortingType;

    public int arraySize = 55;
    public int sortingSpeed = 200;

    final Color START_COLOR = Color.WHITE;
    final Color SELECTED_COLOR =  Color.CORNFLOWERBLUE;
    final Color NEEDS_SWAPPED_COLOR = Color.CRIMSON;

    // Bubble Sort
    private int sortPos = 0;
    private boolean hasSwapped = false;

    // Quick Sort
    private int pivotIndex;
    private double pivotValue;

    private Timeline sortLoopAnimation = new Timeline();
    private int timelineDelay; // milliseconds
    private int timelineKeyCount;

    private KeyFrame evaluatingColorKf;
    private KeyFrame needsEvaluatedColorKf;

    private ObservableList<Double> items = FXCollections.observableArrayList();
    private ObservableList<Rectangle> rectangles;
    private ObservableList<Label> rectLabels;

    public Button btn_GenerateNewArray;
    public Button btn_ShuffleArray;
    public Button btn_SingleStep;
    public Button btn_SinglePass;
    public Button btn_FullSort;

    // Sorting Type Tab Buttons (Menu Bar, Right side).
    public Button activeTitleScreenButton;
    public Button btn_BubbleSort;
    public Button btn_QuickSort;
    public Button btn_MergeSort;
    public Button btn_HeapSort;

    public Label lbl_ChangeArraySize;
    public Label lbl_ArraySizeValue;
    public Label lbl_SortingSpeed;
    public Label lbl_SortingSpeedValue;

    public Slider sldr_ArraySize;
    public Slider sldr_SortingSpeed;

    // Visualization Pane (Contains Visualizer Rectangles).
    public Pane visualizationPane;

    /**
     * Initialization function for the sorting app.
     * Initializes all UI elements with required fields and CSS style classes.
     *
     * @param None.
     * @return None.
     */
    public void initialize()
    {
        // Initialize top menu bar element css classes.
        btn_GenerateNewArray.getStyleClass().add("titlebar-button-inactive");
        btn_ShuffleArray.getStyleClass().add("titlebar-button-inactive");
        btn_SingleStep.getStyleClass().add("titlebar-button-inactive");
        btn_SinglePass.getStyleClass().add("titlebar-button-inactive");
        btn_FullSort.getStyleClass().add("titlebar-button-inactive");
        btn_FullSort.getStyleClass().add("titlebar-button-inactive");

        btn_BubbleSort.getStyleClass().add("titlebar-button-inactive");
        btn_QuickSort.getStyleClass().add("titlebar-button-inactive");
        btn_MergeSort.getStyleClass().add("titlebar-button-inactive");
        btn_HeapSort.getStyleClass().add("titlebar-button-inactive");

        lbl_ChangeArraySize.getStyleClass().add("titlebar-label");
        lbl_ArraySizeValue.getStyleClass().add("titlebar-label");
        lbl_SortingSpeed.getStyleClass().add("titlebar-label");
        lbl_SortingSpeedValue.getStyleClass().add("titlebar-label");

        sldr_ArraySize.getStyleClass().add("menu-slider");
        sldr_SortingSpeed.getStyleClass().add("menu-slider");

        // Initialize event handlers to listen to changing slider values
        sldr_ArraySize.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::UpdateArraySize);
        sldr_SortingSpeed.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::UpdateSortingSpeed);

        // Set slider bars to their default values
        sldr_ArraySize.setValue(this.arraySize);
        lbl_ArraySizeValue.setText(Integer.toString(this.arraySize));
        sldr_SortingSpeed.setValue(this.sortingSpeed);
        lbl_SortingSpeedValue.setText(Integer.toString(this.sortingSpeed));

        /*
            Generate an array and instantiate the visualization for it on program start.
            Uncomment if wanting an initial sorting type to be started with. Otherwise, starts with a blank screen and user can start by choosing.
        */

        // sortingType = "BubbleSort";
        //GenerateList(this.arraySize);
        //InstantiateArrayVisualization();
    }

    /**
     * Selection method to choose the BubbleSort algorithm for sorting visualization.
     * Generates a new array and resets the visualizer upon called.
     * Called through the Bubble Sort button in UI.
     *
     * @param None.
     * @return None.
     */
    public void ActivateBubbleSort()
    {
        sortingType = "BubbleSort";
        ResetSortingVisualizer();

        SetActiveButton(btn_BubbleSort);
    }

    /**
     * Selection method to choose the QuickSort algorithm for sorting visualization.
     * Generates a new array and resets the visualizer upon called.
     * Called through the Quick Sort button in UI.
     *
     * @param None.
     * @return None.
     */
    public void ActivateQuickSort()
    {
        sortingType = "QuickSort";
        ResetSortingVisualizer();

        SetActiveButton(btn_QuickSort);
    }

    /**
     * Selection method to choose the MergeSort algorithm for sorting visualization.
     * Generates a new array and resets the visualizer upon called.
     * Called through the Merge Sort button in UI.
     *
     * @param None.
     * @return None.
     */
    public void ActivateMergeSort()
    {
        sortingType = "MergeSort";
        ResetSortingVisualizer();

        SetActiveButton(btn_MergeSort);
    }

    /**
     * Selection method to choose the HeapSort algorithm for sorting visualization.
     * Generates a new array and resets the visualizer upon called.
     * Called through the Heap Sort button in UI.
     *
     * @param None.
     * @return None.
     */
    public void ActivateHeapSort()
    {
        sortingType = "HeapSort";
        ResetSortingVisualizer();

        SetActiveButton(btn_HeapSort);
    }

    /**
     * Sets the active and inactive style classes of a title screen button when they are selected by the user.
     * Handles hover states, active states, and inactive states when buttons are clicked or hovered.
     * Called by each sorting type button press to set the new active button.
     *
     * @param button        Button which was clicked by the user which is set to now active.
     * @return None.
     */
    private void SetActiveButton(Button button)
    {
        // Set all sorting type buttons to inactive if they aren't the clicked button.
        btn_BubbleSort.getStyleClass().clear();
        btn_QuickSort.getStyleClass().clear();
        btn_MergeSort.getStyleClass().clear();
        btn_HeapSort.getStyleClass().clear();

        if (btn_BubbleSort != button) {
            btn_BubbleSort.getStyleClass().add("titlebar-button-inactive");
        }

        if (btn_QuickSort != button) {
            btn_QuickSort.getStyleClass().add("titlebar-button-inactive");
        }

        if (btn_MergeSort != button) {
            btn_MergeSort.getStyleClass().add("titlebar-button-inactive");
        }

        if (btn_HeapSort != button) {
            btn_HeapSort.getStyleClass().add("titlebar-button-inactive");
        }

        // Add active button class to only clicked button.
        button.getStyleClass().clear();
        button.getStyleClass().add("titlebar-button-active");
        activeTitleScreenButton = button;
    }

    /**
     * Update the sorting visualizer by reinitializing the values (items) array
     * with new random values to fit the current given arraySize.
     *
     * Called to reinitialize the visualizer when the arraySize is changed.
     *
     * @param None.
     * @return None.
     */
    public void ResetSortingVisualizer()
    {
        // Stop any sorting animation that is playing.
        sortLoopAnimation.stop();
        // Reset sorting position iterator to 0.
        sortPos = 0;
        // Reinitialize the values array list
        GenerateList(this.arraySize);
        // Clear and recreate the visualizer rectangles with the new array list.
        InstantiateArrayVisualization();
    }

    /**
     * Helper function for the ArraySize slider mouse event.
     * Updates the arraySize field when ArraySize slider is changed.
     *
     * @param e         MouseEvent event handler for button click function.
     * @return None.
     */
    private void UpdateArraySize(MouseEvent e)
    {
        this.arraySize = (int)sldr_ArraySize.getValue();
        lbl_ArraySizeValue.setText(Integer.toString(this.arraySize));

        // Generate the new rectangle visualizer array when array size is changed.
        // Check if sort animation is playing when array size is changed. If so, stop the animation.
        if (sortLoopAnimation.getCurrentRate() != 0.0)
            sortLoopAnimation.stop();
        ResetSortingVisualizer();
    }

    /**
     * Helper function for the SortingSpeed slider mouse event.
     * Updates the sortingSpeed field when SortingSpeed slider is changed.
     *
     * @param e         MouseEvent event handler for button click function.
     * @return None.
     */
    private void UpdateSortingSpeed(MouseEvent e)
    {
        this.sortingSpeed = (int)sldr_SortingSpeed.getValue();
        lbl_SortingSpeedValue.setText(Integer.toString(this.sortingSpeed));
    }

    /**
     * Instantiates the over-all values array list.
     * Used to represent and fill visualizer rectangles with values.
     *
     * Called on program start to initialize default fields.
     * Called when arraySize and sortingSpeed sliders are changed to reinstantiate the updated array.
     *
     * @param arraySize     Size of the new array to be generated and randomly filled.
     * @return None.
     */
    private void GenerateList(int arraySize)
    {
        items = FXCollections.observableArrayList();
        for (int i = 0; i < arraySize; i++) {
            // double value for array list values to be sorted.
            items.add(Math.random() * (visualizationPane.getPrefHeight() / 1.7));
            // used for height of rectangles (proportional to the height of their pane).
        }
    }

    /**
     * Instantiates the rectangle visualization array for the sorting algorithm.
     * Calculates required rectangle width using arraySize, fills the visualizer pane with
     * new rectangles with updated fields.
     *
     * Called on program start to initialize default fields.
     * Called when arraySize and sortingSpeed sliders are changed to reinstantiate the updated array.
     *
     * @param None.
     * @return None.
     * */
    private void InstantiateArrayVisualization()
    {
        // Clear visualization pane of current rectangles and rectangle labels when new array is initialized.
        visualizationPane.getChildren().removeIf(Rectangle.class::isInstance);
        visualizationPane.getChildren().removeIf(Label.class::isInstance);

        // Reinitialize rectangles array to clear it when changed to a new size.
        rectangles = FXCollections.observableArrayList();
        rectLabels = FXCollections.observableArrayList();

        double rectWidth = (visualizationPane.getPrefWidth()) / arraySize - 7;
        double spacing = 8;

        // Loop through items value array and create a rectangle with their corresponding height and add them to the visualizer pane.
        for (int i = 0; i < items.size(); i++)
        {
            double rectHeight = items.get(i);
            rectangles.add(i, new Rectangle((i * rectWidth) + (i * spacing), 0, rectWidth, rectHeight));
            rectangles.get(i).setFill(START_COLOR);
            rectangles.get(i).setStroke(Color.BLACK);
            // Create a new rectangle with its given height representing its value, place sequentially into the visualizer pane.
            visualizationPane.getChildren().add(rectangles.get(i));

            if (arraySize < 26)
            {
                rectLabels.add(i, new Label(Integer.toString((int)rectangles.get(i).getHeight())));
                rectLabels.get(i).setLayoutX((i * rectWidth) + (i * spacing) + (rectWidth / 3));
                if (rectHeight > 50)
                    rectLabels.get(i).setLayoutY(rectHeight - 25);
                else
                    rectLabels.get(i).setLayoutY(rectHeight + 6);
                rectLabels.get(i).setTextFill(Color.BLACK);
                rectLabels.get(i).setAlignment(Pos.CENTER);
                visualizationPane.getChildren().add(rectLabels.get(i));
            }
        }
    }

    /**
     * Swap function for handling arrays that are being shuffled and sorted.
     * Swaps two array elements at their corresponding indices.
     *
     * Currently handles:
     *  The over-all value array (items) which represent the height of visualizer rectangles.
     *  The rectangle visualizer array to swap both rectangle x-positions on the visualizer pane, and their index positions in the array.
     *  Can be used to handle any further visualization elements in the future.
     *
     * @param pos1      Index for the first element to be swapped.
     * @param pos2      Index for the second element to be swapped.
     * @return None.
     */
    public void swap(int pos1, int pos2)
    {
        // Swap item values in the base (integer) items array that rectangles represent.
        double items1val = items.get(pos1);
        double items2val = items.get(pos2);
        items.set(pos1, items2val);
        items.set(pos2, items1val);

        // Swap positions of two passed rectangles in visualizer pane.
        double rect1x = rectangles.get(pos1).getX();
        double rect2x = rectangles.get(pos2).getX();
        rectangles.get(pos1).setX(rect2x);
        rectangles.get(pos2).setX(rect1x);

        // Swap indices of the two rectangles in the rectangles array to keep them properly.
        Rectangle cachedRect1 = rectangles.get(pos1);
        rectangles.set(pos1, rectangles.get(pos2));
        rectangles.set(pos2, cachedRect1);

        // Swap the positions of the rectangle's width labels when rectangles are swapped
        if (rectLabels.size() > 0) // Check if labels have been created, only filled when arraySize is small enough.
        {
            // Swap x- positions of the two labels.
            double label1x = rectLabels.get(pos1).getLayoutX();
            double label2x = rectLabels.get(pos2).getLayoutX();
            rectLabels.get(pos1).setLayoutX(label2x);
            rectLabels.get(pos2).setLayoutX(label1x);

            // Swap array positions of the two labels.
            Label cachedLabel1 = rectLabels.get(pos1);
            rectLabels.set(pos1, rectLabels.get(pos2));
            rectLabels.set(pos2, cachedLabel1);
        }
    }

    /**
     * Shuffles the current active integer array which is to be sorted.
     * Does not change any values or recreate the array.
     *
     * @param None.
     * @return None.
     */
    public void ShuffleCurrentArray() {
        // Prevent shuffling if no array has been initialized or a sort is currently in progress.
        if (items.size() == 0 || sortLoopAnimation.getCurrentRate() != 0.0) return;
        // Make a new timeline and fill it with the new keyframes.
        sortLoopAnimation = new Timeline();
        timelineDelay = 50; // milliseconds
        timelineKeyCount = 0;

        for (int i = 0; i < (items.size() + 15) * 2; i++) {
            timelineKeyCount++;
            // Make a new keyframe for each loop
            KeyFrame kf = new KeyFrame(Duration.millis(timelineKeyCount * timelineDelay), ae -> {
                // Perform logic for each keyframe step in the timeline
                swap(random.nextInt(items.size()), random.nextInt(items.size()));
            });

            sortLoopAnimation.getKeyFrames().add(kf);
        }
        // Done building keyframes for every loop.
        sortLoopAnimation.play();
    }

    /**
     * Bubble sorting algorithm function used through single step, single pass, and full sort methods.
     * Performs the logic behind comparing two values in an array
     * and swapping them to sort based on item size following the Bubble Sort algorithm.
     *
     * @param array     (double) Array for the bubble sort to be performed on.
     * @return None.
     */
    private void BubbleSort(ObservableList<Double> array)
    {
        if (sortPos == 0) hasSwapped = false;

        double v1 = array.get(sortPos);
        double v2 = array.get(sortPos + 1);

        if (v1 > v2)
        {
            // left side rectangle is bigger than right side rectangle, swap their positions.
            swap(sortPos, sortPos + 1);
            hasSwapped = true;
        }
        sortPos++;
    }

    /**
     * Quick sorting algorithm function used through full sort method.
     * Performs the logic behind recursively calling partitioning to divide and sort the passed array.
     *
     * @param array     (double) Array for the quick sort to be performed on.
     * @param startPos  Starting position for the recursive sorting to begin.    (Can be at any position in the divided array for a recursive iteration, begins at start of array).
     * @param endPos    Ending position for the recursive sorting to end.        (Can be at any position in the divided array for a recursive iteration, begins at end of array).
     * @return None.
     */
    private void QuickSortFullSort(ObservableList<Double> array, int startPos, int endPos)
    {
        if (startPos >= endPos) return;

        Partition(array, startPos, endPos);
    }

    /**
     * Partitioning method for the QuickSort algorithm.
     * Performs the recursive division of the passed array by swapping array values to separate the array at the pivot.
     * Calls itself at the end of its functionality to repeat recursively.
     * Currently uses keyframes for each individual full pass, not each individual rectangle swap.
     * Visualization occurs too quickly and instantly, needs to be changed.
     *
     * @param array     (double) Array for the quick sort to be performed on.
     * @param startPos  Starting position for the recursive sorting to begin.    (Can be at any position in the divided array for a recursive iteration, begins at start of array).
     * @param endPos    Ending position for the recursive sorting to end.        (Can be at any position in the divided array for a recursive iteration, begins at end of array).
     * @return None.
     */
    private void Partition(ObservableList<Double> array, int startPos, int endPos)
    {
        sortLoopAnimation = new Timeline();
        timelineDelay = 300 - sortingSpeed;
        timelineKeyCount = 1;

        KeyFrame sortKf = new KeyFrame(Duration.millis(timelineKeyCount * timelineDelay), ae -> {
            pivotIndex = startPos;
            double pivotValue = array.get(endPos);
            for (int i = startPos; i <= endPos; i++) {
                timelineKeyCount++;

                if (i < endPos) {
                    if (array.get(i) < pivotValue) {
                        swap(i, pivotIndex);
                        pivotIndex++;
                    }
                } else if (i == endPos) {
                    swap(pivotIndex, endPos);
                }
            }
        });

        sortLoopAnimation.getKeyFrames().add(sortKf);
        sortLoopAnimation.setOnFinished(event -> {
            QuickSortFullSort(array, startPos, pivotIndex - 1);
            QuickSortFullSort(array, pivotIndex + 1, endPos);
        });
        sortLoopAnimation.play();
    }

    /**
     * Quick sorting algorithm for the full sort functionality.
     * Uses keyframes for each individual swap function in a recursive pass through the quick sort algorithm.
     * Currently not working. Local variables for loop iterations are not usable inside the keyframes for each swap,
     * therefore the recursive keyframes cannot be made inside the loop using the iterator (i).
     * After some research, ParallelTransitions may work, will be trying next.
     *
     * @param array     (double) Array for the quick sort to be performed on.
     * @param startPos  Starting position to subdivide and sort at in the sorting array.
     * @param endPos    Ending position to subdivide and sort at in the sorting array.
     * @return None.
     */
    private void PartitionKeyFramesNotWorking(ObservableList<Double> array, int startPos, int endPos)
    {
        Timeline sortLoopAnimation = new Timeline();
        timelineDelay = 300 - sortingSpeed;
        timelineKeyCount = 0;

        pivotIndex = startPos;
        pivotValue = array.get(endPos);

        for (int i = startPos; i <= endPos; i++)
        {
            timelineKeyCount++;
            int finalI = i;
            KeyFrame sortKf = new KeyFrame(Duration.millis(timelineKeyCount * timelineDelay), ae -> {
                if (finalI < endPos) {
                    if (array.get(finalI) < pivotValue) {
                        // Swap any element smaller than the pivot to the left side of the pivot (pivot = last element)
                        swap(finalI, pivotIndex);
                        // Increment pivot index
                        pivotIndex++;
                    }
                }
                else if (finalI == endPos) {
                    swap(pivotIndex, endPos);
                }
            });
            sortLoopAnimation.getKeyFrames().add(sortKf);
        }

        sortLoopAnimation.play();
        sortLoopAnimation.setOnFinished(event -> {
            QuickSortFullSort(array, startPos, pivotIndex - 1);
            QuickSortFullSort(array, pivotIndex + 1, endPos);
        });
    }

    /**
     * Single step for the Bubble Sort sorting algorithm functionality.
     * Performs a single sort iteration in the passed (double)array depending on the current sorting type option.
     * Called using the "Single Step" Button in the UI.
     *
     * @param actionEvent       Action event handler for the single step button press event.
     * @return None.
     */
    public void SingleStep(ActionEvent actionEvent)
    {
        // Check if animation is already playing.
        if (sortLoopAnimation.getCurrentRate() != 0.0) return;
        sortLoopAnimation = new Timeline();
        timelineDelay = 300 - sortingSpeed;
        timelineKeyCount = 0;

        if (sortingType == "BubbleSort")
        {
            // Rectangle color highlighting keyframes.

            // Set currently evaluating rectangles to blue.
            KeyFrame evaluatingColorKf = new KeyFrame(Duration.millis(0), ae -> {
                rectangles.get(sortPos).setFill(SELECTED_COLOR);
                rectangles.get(sortPos + 1).setFill(SELECTED_COLOR);
            });

            // Set currently evaluating rectangles to red if they are out of order (need to be swapped).
            KeyFrame needsEvaluatedColorKf = new KeyFrame(Duration.millis(timelineDelay / 2), ae -> {
                if (items.get(sortPos) > items.get(sortPos + 1)) {
                    rectangles.get(sortPos).setFill(NEEDS_SWAPPED_COLOR);
                    rectangles.get(sortPos + 1).setFill(NEEDS_SWAPPED_COLOR);
                }
            });

            // Single step sorting keyframe.
            KeyFrame sortKf = new KeyFrame(Duration.millis(timelineDelay), ae -> {
                BubbleSort(items);
                // Reset fill colour of previous two evaluated rectangles to white. Occurs after sortPos is incremented.
                rectangles.get(sortPos - 1).setFill(START_COLOR);
                rectangles.get(sortPos).setFill(START_COLOR);
            });

            sortLoopAnimation.getKeyFrames().add(evaluatingColorKf);
            sortLoopAnimation.getKeyFrames().add(needsEvaluatedColorKf);
            sortLoopAnimation.getKeyFrames().add(sortKf);
            sortLoopAnimation.play();

            // Nothing left to compare last rectangle to, reset sortPos to beginning.
            if (sortPos == items.size() - 1) sortPos = 0;
        }
    }

    /**
     * Single pass sorting functionality.
     * Performs a single sorting pass from the beginning to the end of the passed (double)array.
     * Called in the "Single Pass" button in the UI.
     *
     * @param None.
     * @return None.
     */
    public void SinglePass()
    {
        // Check if animation is already playing.
        if (sortLoopAnimation.getCurrentRate() != 0.0) return;
        // Make a new timeline and fill it with the new keyframes.
        sortLoopAnimation = new Timeline();
        timelineDelay = 300 - sortingSpeed; // milliseconds
        timelineKeyCount = 0;
        // Start the pass at start of array:
        sortPos = 0;

        if (sortingType == "BubbleSort")
        {
            for (int i = 0; i < items.size() - 1; i++) {
                timelineKeyCount++;
                // Make a new colour keyframe for each loop, set currently evaluating rectangles to blue.
                // Occurs at the start of every sort keyframe, therefore (timelineKeyCount - 1) * timeDelay.
                evaluatingColorKf = new KeyFrame(Duration.millis((timelineKeyCount - 1) * timelineDelay), ae -> {
                    rectangles.get(sortPos).setFill(Color.CORNFLOWERBLUE);
                    rectangles.get(sortPos + 1).setFill(SELECTED_COLOR);
                });

                needsEvaluatedColorKf = new KeyFrame(Duration.millis(((timelineKeyCount - 1) * timelineDelay) + (timelineDelay / 2)), ae -> {
                    if (items.get(sortPos) > items.get(sortPos + 1)) {
                        rectangles.get(sortPos).setFill(Color.RED);
                        rectangles.get(sortPos + 1).setFill(Color.RED);
                    }
                });

                // Make a new sorting keyframe for each loop
                KeyFrame sortKf = new KeyFrame(Duration.millis(timelineKeyCount * timelineDelay), ae -> {
                    // Perform logic for each keyframe step in the timeline
                    BubbleSort(items);
                    // Set evaluated rectangles back to default color, occurs after sortPos is incremented.
                    rectangles.get(sortPos - 1).setFill(START_COLOR);
                    rectangles.get(sortPos).setFill(START_COLOR);
                });

                // Make sure these keyframes have been set before adding and playing the animation.
                // If no sort type is selected, no keyframes are built as keyframes are dependent on the sort type.
                sortLoopAnimation.getKeyFrames().add(evaluatingColorKf);
                sortLoopAnimation.getKeyFrames().add(needsEvaluatedColorKf);
                sortLoopAnimation.getKeyFrames().add(sortKf);
            }
            // Done building keyframes for every loop.
            sortLoopAnimation.play();
        }

        // Scale other sorting algorithms to be added.
        else if (sortingType == "QuickSort")
        {

        }

        else if (sortingType == "MergeSort")
        {

        }

        else if (sortingType == "HeapSort")
        {

        }

    }

    /**
     * Recursive full pass sorting functionality.
     * Performs a recursive sorting pass from the beginning to the end of the passed (double)array.
     * Upon reaching the end of a pass, rests to start and calls another pass if not fully sorted.
     *
     * @param None.
     * @return None.
     */
    public void FullSort()
    {
        // Check if animation is already playing. Prevent from starting new if so.
        if (sortLoopAnimation.getCurrentRate() != 0.0) return;
        // Make a new timeline and fill it with the new keyframes.
        sortLoopAnimation = new Timeline();
        timelineDelay = 300 - sortingSpeed; // milliseconds
        timelineKeyCount = 0;
        // Start the pass at start of array:
        sortPos = 0;

        if (sortingType == "BubbleSort")
        {
            for (int i = 0; i < items.size() - 1; i++) {
                timelineKeyCount++;
                // Make a new colour keyframe for each loop, set currently evaluating rectangles to blue.
                // Occurs at the start of every sort keyframe, therefore (timelineKeyCount - 1) * timeDelay.
                evaluatingColorKf = new KeyFrame(Duration.millis((timelineKeyCount - 1) * timelineDelay), ae -> {
                    rectangles.get(sortPos).setFill(SELECTED_COLOR);
                    rectangles.get(sortPos + 1).setFill(SELECTED_COLOR);
                });

                needsEvaluatedColorKf = new KeyFrame(Duration.millis(((timelineKeyCount - 1) * timelineDelay) + (timelineDelay / 2)), ae -> {
                    if (items.get(sortPos) > items.get(sortPos + 1)) {
                        rectangles.get(sortPos).setFill(Color.RED);
                        rectangles.get(sortPos + 1).setFill(Color.RED);
                    }
                });

                // Make a new sorting keyframe for each loop
                KeyFrame sortKf = new KeyFrame(Duration.millis(timelineKeyCount * timelineDelay), ae -> {
                    // Perform logic for each keyframe step in the timeline
                    BubbleSort(items);
                    // Set evaluated rectangles back to default color, occurs after sortPos is incremented.
                    rectangles.get(sortPos - 1).setFill(START_COLOR);
                    rectangles.get(sortPos).setFill(START_COLOR);
                });

                // Make sure these keyframes have been set before adding and playing the animation.
                // If no sort type is selected, no keyframes are built as keyframes are dependent on the sort type.
                sortLoopAnimation.getKeyFrames().add(evaluatingColorKf);
                sortLoopAnimation.getKeyFrames().add(needsEvaluatedColorKf);
                sortLoopAnimation.getKeyFrames().add(sortKf);
            }
            // Done building keyframes for every loop.
            sortLoopAnimation.play();
            sortLoopAnimation.setOnFinished(event -> {
               if (hasSwapped)
               {
                   // hasSwapped is set to true if there is ever a need to swap the rectangles in a pass.
                   // If a rectangle was needed to be swapped last pass, call another pass to continue sorting.
                   // Otherwise done sorting, stop passing.
                   FullSort();
               }
            });
        }
        else if (sortingType == "QuickSort")
        {
            // Start the first call for the quicksort recursive algorithm, use 0 to end of the array for start and end positions.
            QuickSortFullSort(items, 0, items.size() - 1);
        }

    }
}
