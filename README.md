# 2048Host
A host for a game of 2048 for RHHS Compsci Club, Python group
<br>
AIs will download the file MoveDecider.py that will have a method called decideMove(), and the AI will return a direction to move in.
<br>
<br>
The decideMove() method will be provided with two parameters: a list of lists, and a boolean.
<br>
The list of lists represents the grid, with rows first, then columns. So if the list were to be [[0,2,2],[0,0,0],[0,2,4]], then the first row from left to right would be 0,2,2; the second row would be 0,0,0; and the third row would be 0,2,4. You will then return an integer where 0 represents SWIPE UP, 1 represents RIGHT, 2 DOWN, and 3 LEFT.
<br>
For example,
<br>
This graph:
![image](https://cloud.githubusercontent.com/assets/10538710/12337360/9282063e-bad7-11e5-89bd-47376108012c.png)
<br>
Can be represented using <br>
{{4,2,2,0},<br>
{4,4,0,0},<br>
{2,0,0,0},<br>
{0,0,2,0}}<br>
<br>
The boolean represents if anything has changed on the grid. True means something has changed, false means nothing has changed.
