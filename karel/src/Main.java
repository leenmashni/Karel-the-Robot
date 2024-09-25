import stanford.karel.SuperKarel;

public class Main extends SuperKarel {

    int steps=0;
    int beepersUsed = 0;
    // To put down Beeper
    public void placeBeeper(){
        if (noBeepersPresent()){
            putBeeper();
            ++beepersUsed;
        }
    }
    // To pick up Beeper
    public void collectBeeper(){
            if (beepersPresent()){
                pickBeeper();
                --beepersUsed;
            }
    }
    //To take one step forward
    public void oneStep(boolean collectBeeper, boolean placeBeeper) {
        move();
        ++steps;
        if (collectBeeper && placeBeeper) {
            return;
        }
        if (collectBeeper) {
            collectBeeper();
        }
        else if (placeBeeper) {
            placeBeeper();
        }
    }
    //To move to the edge
    public void moveToEnd(boolean collectBeeper, boolean placeBeeper) {
        while (frontIsClear())
            oneStep(collectBeeper, placeBeeper);
    }
    //Dive into Four Parts
    public void divideFourParts(int width , int height) {
        if (width > 2 && height > 2) {                      //when width and height > 2
            if (width % 2 == 0 && height % 2 == 0) {           // Even X Even
                divideEvenHeight(height);
                divideEvenWidth(width);
            } else if (width % 2 == 0) {                      // Even X Odd
                divideOddHeight(height);
                divideEvenWidth(width);
            } else if (height % 2 == 0){                     // Odd X Even
                divideEvenHeight(height);
                divideOddWidth(width);
            } else{
                divideOddHeight(height);                    // Odd X Odd
                divideOddWidth(width);
            }
        }
        else if (width <2){                             // Width = 1 , Height !=1
            if (height % 2 == 0)
                divideEvenHeight(height);
            else
                divideOddHeight(height);
        }
        else {             // Width != 1 , Height = 1
                oneHorizontalLine(width);
        }
    }
    //Divide one Horizontal Line
    public void oneHorizontalLine(int Width) {
        turnLeft();
        turnLeft();
        if (Width % 2 == 0) {
            Width = Width / 2;
            for (int i = 0; i < Width - 1; ++i)
                oneStep(true, false);
            placeBeeper();
            oneStep(true, false);
            placeBeeper();
            moveToEnd(true, false);
            turnLeft();
            turnLeft();
        } else {
            Width = Width / 2 + 1;
            for (int i = 1; i < Width; ++i) {
                oneStep(true, false);
            }
            placeBeeper();
            moveToEnd(true, false);
            turnLeft();
            turnLeft();
        }
    }
    //Divide Even Height
    public void divideEvenHeight(int Height){
        Height = Height / 2;
        turnRight();
        for (int i = 0; i < Height; ++i)
            oneStep(false , false);

        turnRight();
        placeBeeper();
        moveToEnd(false , true);
        turnRight();
        oneStep(false , true);
        turnRight();
        moveToEnd(false , true);
        turnLeft();
        moveToEnd(false , false);
        turnLeft();
    }
    //Divide Odd Height
    public void divideOddHeight(int Height){
        Height =Height / 2 + 1;
        turnRight();
        for (int i = 1; i < Height; ++i){
            oneStep(true , false);
        }
        turnRight();
        placeBeeper();
        moveToEnd(false , true);
        turnRight();
        oneStep(false , false);
        turnRight();
        moveToEnd(false , false);
        turnLeft();
        moveToEnd(false , false);
        turnLeft();
    }
    //Divide Even Width
    public void divideEvenWidth(int Width){
        Width = Width / 2 ;
        for (int i = 0; i < Width; ++i){
            oneStep(true , false);
        }
        turnLeft();
        placeBeeper();
        moveToEnd(false , true);
        turnLeft();
        oneStep(false , true);
        turnLeft();
        moveToEnd(false , true);
    }
    //Divide Odd Width
    public void divideOddWidth(int Width){
        Width = Width / 2 + 1;
        for (int i = 1; i < Width; ++i){
            oneStep(true , false);
        }
        turnLeft();
        placeBeeper();
        moveToEnd(false , true);
    }
    //Special Case : when 2 X 2
    private void diagonalMove() {
        turnRight();
        placeBeeper();
        oneStep(false, false);
        turnRight();
        oneStep(false, true);
        turnLeft();
        turnLeft();
    }


    @Override
    public void run() {
        int width = 1;
        int height = 1;
        setBeepersInBag(1000);         // Number of Beepers = 1000
        turnLeft();
        while (frontIsClear()){               // Measure Height
            oneStep(true ,false);
            ++height;
        }
        turnRight();
        while (frontIsClear()){              // Measure Width
            oneStep(true ,false);
            ++width;
        }
        //Special Case : Width , Height < 2 -> can't divide
        if (width < 2 && height < 2)
            return ;
        else if (width == 2 && height == 2) {
            diagonalMove();
        }
        else
           divideFourParts(width , height);

        System.out.println("steps = " + steps);
        System.out.println("beepersUsed = " + beepersUsed);
    }
}

