# 2048Host
A host for a game of 2048 for RHHS Compsci Club, Python group
<br>
AIs will download the file MoveDecider.py that will have a method called decideMove(), and the AI will return a direction to move in.
<br>
<br>
The decideMove() method will accept a list of lists, with rows first, then columns. So if the list were to be [[0,2,2],[0,0,0],[0,2,4]], then the first row from left to right would be 0,2,2; the second row would be 0,0,0; and the third row would be 0,2,4. You will then return an integer where 0 represents SWIPE UP, 1 represents RIGHT, 2 DOWN, and 3 LEFT.
