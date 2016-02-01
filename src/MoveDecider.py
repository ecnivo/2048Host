def initialize():
	global number
	number = 0

def decideMove(inputArray, changedBool):
	direction = 0
	global number
	number += 1
	# 0 = NORTH/UP
	# 1 = EAST/RIGHT
	# 2 = SOUTH/DOWN
	# 3 = WEST/LEFT
	return (direction + number) % 4