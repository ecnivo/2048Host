import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.python.core.PyBoolean;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

@SuppressWarnings("serial")
public class GameManager extends JPanel {

	private PythonInterpreter	interpreter;
	private TwentyFortyEight	twfe;
	private ArrayList<MoveInfo>	moves;
	private ArrayList<MoveInfo>	merge;
	protected double			tileSize;

	private final static byte	UP			= 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	private final static int	ANIM_STEPS	= 75;
	private final static double	ANIM_SPEED	= 70;

	public GameManager(TwentyFortyEight twfe) {
		this.twfe = twfe;
		interpreter = new PythonInterpreter();
		interpreter.exec("from MoveDecider import *");
		interpreter.exec("initialize()");
		setLayout(null);
		moves = new ArrayList<MoveInfo>();
		merge = new ArrayList<MoveInfo>();
		updateTileSize();

		Thread loop = new Thread(new Runnable() {

			@Override
			public void run() {
				boolean changed = false;
				long swipes = 0;
				while (true) {
					interpreter.exec("from MoveDecider import decideMove");
					PyList pyGrid = getGrid();
					PyBoolean changedBool;
					if (changed) {
						changedBool = new PyBoolean(true);
					} else {
						changedBool = new PyBoolean(false);
					}
					interpreter.set("inputArray", pyGrid);
					interpreter.set("changedBool", changedBool);
					PyObject output = interpreter.eval("decideMove(inputArray, changedBool)");
					int direction = (int) output.__tojava__(int.class);
					// direction = (int) (Math.random() * 4);

					changed = false;
					switch (direction) {
						case UP:
							System.out.println("[INFO] UP was chosen");
							for (int col = 0; col < twfe.getGrid().length; col++) {
								for (int row = 0; row < twfe.getGrid().length - 1; row++) {
									NumberTile current = twfe.getGrid()[row][col];
									if (current != null) {
										int nextRow = row + 1;
										while (nextRow < twfe.getGrid()[0].length - 1 && twfe.getGrid()[nextRow][col] == null) {
											nextRow++;
										}
										NumberTile next = twfe.getGrid()[nextRow][col];
										if (next == null) {
											break;
										} else if (current.getValue() == next.getValue()) {
											current.doubleValue();
											merge.add(new MoveInfo(twfe.getGrid()[nextRow][col], MoveInfo.VERTICAL, nextRow, row));
											twfe.getGrid()[nextRow][col] = null;
											row = nextRow;
											changed = true;
										}
									}
								}
							}
							for (int col = 0; col < twfe.getGrid().length; col++) {
								for (int row = 0; row < twfe.getGrid()[0].length - 1; row++) {
									if (twfe.getGrid()[row][col] == null) {
										int nextRow = row + 1;
										while (nextRow < twfe.getGrid().length - 1 && twfe.getGrid()[nextRow][col] == null) {
											nextRow++;
										}
										NumberTile nextItem = twfe.getGrid()[nextRow][col];
										if (nextItem != null) {
											twfe.getGrid()[row][col] = nextItem;
											moves.add(new MoveInfo(nextItem, MoveInfo.VERTICAL, nextRow, row));
											twfe.getGrid()[nextRow][col] = null;
											changed = true;
										} else {
											break;
										}
									}
								}
							}
							break;

						case RIGHT:
							System.out.println("[INFO] RIGHT was chosen");
							for (NumberTile[] row : twfe.getGrid()) {
								for (int col = row.length - 1; col > 0; col--) {
									NumberTile current = row[col];
									if (current != null) {
										int nextCol = col - 1;
										while (nextCol > 0 && row[nextCol] == null) {
											nextCol--;
										}
										NumberTile next = row[nextCol];
										if (next == null) {
											break;
										} else if (current.getValue() == next.getValue()) {
											current.doubleValue();
											merge.add(new MoveInfo(row[nextCol], MoveInfo.HORIZONTAL, nextCol, col));
											row[nextCol] = null;
											col = nextCol;
											changed = true;
										}
									}
								}
							}
							for (NumberTile[] row : twfe.getGrid()) {
								for (int col = row.length - 1; col > 0; col--) {
									if (row[col] == null) {
										int nextCol = col - 1;
										while (nextCol > 0 && row[nextCol] == null) {
											nextCol--;
										}
										NumberTile nextItem = row[nextCol];
										if (nextItem != null) {
											row[col] = nextItem;
											changed = true;
											moves.add(new MoveInfo(nextItem, MoveInfo.HORIZONTAL, nextCol, col));
											row[nextCol] = null;
										} else {
											break;
										}
									}
								}
							}
							break;

						case DOWN:
							System.out.println("[INFO] DOWN was chosen");
							for (int col = 0; col < twfe.getGrid().length; col++) {
								for (int row = twfe.getGrid()[0].length - 1; row > 0; row--) {
									NumberTile current = twfe.getGrid()[row][col];
									if (current != null) {
										int nextRow = row - 1;
										while (nextRow > 0 && twfe.getGrid()[nextRow][col] == null) {
											nextRow--;
										}
										NumberTile next = twfe.getGrid()[nextRow][col];
										if (next == null) {
											break;
										} else if (current.getValue() == next.getValue()) {
											current.doubleValue();
											merge.add(new MoveInfo(twfe.getGrid()[nextRow][col], MoveInfo.VERTICAL, nextRow, row));
											twfe.getGrid()[nextRow][col] = null;
											row = nextRow;
											changed = true;
										}
									}
								}
							}
							for (int col = 0; col < twfe.getGrid().length; col++) {
								for (int row = twfe.getGrid().length - 1; row > 0; row--) {
									if (twfe.getGrid()[row][col] == null) {
										int nextRow = row - 1;
										while (nextRow > 0 && twfe.getGrid()[nextRow][col] == null) {
											nextRow--;
										}
										NumberTile nextItem = twfe.getGrid()[nextRow][col];
										if (nextItem != null) {
											twfe.getGrid()[row][col] = nextItem;
											twfe.getGrid()[nextRow][col] = null;
											moves.add(new MoveInfo(nextItem, MoveInfo.VERTICAL, nextRow, row));
											changed = true;
										} else {
											break;
										}
									}
								}
							}
							break;

						case LEFT:
							System.out.println("[INFO] LEFT was chosen");
							for (NumberTile[] row : twfe.getGrid()) {
								for (int col = 0; col < row.length - 1; col++) {
									NumberTile current = row[col];
									if (current != null) {
										int nextCol = col + 1;
										while (nextCol < row.length - 1 && row[nextCol] == null) {
											nextCol++;
										}
										NumberTile next = row[nextCol];
										if (next == null) {
											break;
										} else if (current.getValue() == next.getValue()) {
											current.doubleValue();
											merge.add(new MoveInfo(row[nextCol], MoveInfo.HORIZONTAL, nextCol, col));
											row[nextCol] = null;
											col = nextCol;
											changed = true;
										}
									}
								}
							}
							for (NumberTile[] row : twfe.getGrid()) {
								for (int col = 0; col < row.length - 1; col++) {
									if (row[col] == null) {
										int nextCol = col + 1;
										while (nextCol < row.length - 1 && row[nextCol] == null) {
											nextCol++;
										}
										NumberTile nextItem = row[nextCol];
										if (nextItem != null) {
											row[col] = nextItem;
											row[nextCol] = null;
											moves.add(new MoveInfo(nextItem, MoveInfo.HORIZONTAL, nextCol, col));
											changed = true;
										} else {
											break;
										}
									}
								}
							}
							break;
					}

					if (!changed) {
						continue;
					}

					for (int step = 0; step <= ANIM_STEPS; step++) {
						for (MoveInfo move : moves) {
							NumberTile tile = move.getTile();
							switch (move.getAxis()) {
								case MoveInfo.HORIZONTAL:
									tile.incrementLeft(move.getIncrementAmount());
									break;

								case MoveInfo.VERTICAL:
									tile.incrementUp(move.getIncrementAmount());
									break;
							}
						}
						for (MoveInfo mergeMove : merge) {
							NumberTile tile = mergeMove.getTile();
							switch (mergeMove.getAxis()) {
								case MoveInfo.HORIZONTAL:
									tile.incrementLeft(mergeMove.getIncrementAmount());
									break;

								case MoveInfo.VERTICAL:
									tile.incrementUp(mergeMove.getIncrementAmount());
									break;
							}
						}
						revalidate();
						repaint();
						try {
							Thread.sleep((int) (250 / ANIM_SPEED));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					for (int row = 0; row < twfe.getGrid().length; row++) {
						for (int column = 0; column < twfe.getGrid()[row].length; column++) {
							try {
								twfe.getGrid()[row][column].setPosition(new Point((int) ((column + 0.05) * twfe.getManager().tileSize), (int) ((row + 0.05) * twfe.getManager().tileSize)));
							} catch (NullPointerException e) {
							}
						}
					}

					try {
						Thread.sleep(250);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

					new NumberTile(twfe);

					moves.clear();
					merge.clear();

					revalidate();
					repaint();

					swipes++;

					for (int row = 0; row < twfe.getGrid().length; row++) {
						for (int col = 0; col < twfe.getGrid().length; col++) {
							if (twfe.getGrid()[row][col].getValue() == 2048) {
								JOptionPane.showConfirmDialog(null, "You completed 2048 in " + swipes + " moves!", "Complete!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						}
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		loop.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		updateTileSize();

		Color background = new Color(0x90C3D4);
		Color lines = new Color(0x9CB5B5);
		g.setColor(background);
		g.fillRect(0, 0, getActualWidth(), getActualWidth());

		for (MoveInfo move : merge) {
			g.setColor(new Color(128, 32, 64));
			g.fillRect((int) move.getTile().getPosition().getX(), (int) move.getTile().getPosition().getY(), (int) (tileSize * 0.9), (int) (tileSize * 0.9));
		}

		for (int row = 0; row < twfe.getGrid().length; row++) {
			for (int col = 0; col < twfe.getGrid()[row].length; col++) {
				g.setColor(lines);
				g.fillRect((int) (Math.round(col * tileSize)), (int) (Math.round(row * tileSize)), 2, (int) Math.round(tileSize));
				g.fillRect((int) (Math.round(col * tileSize)), (int) (Math.round(row * tileSize)), (int) Math.round(tileSize), 2);

				if (twfe.getGrid()[row][col] != null) {
					NumberTile tile = twfe.getGrid()[row][col];
					g.setColor(new Color(128, 32, 64));
					// TODO replace with algorithm to change depending on tile
					// value

					// int x = (int) ((col + 0.05) * getTileSize());
					// int y = (int) ((row + 0.05) * getTileSize());
					g.fillRect((int) Math.round(tile.getPosition().getX()), (int) Math.round(tile.getPosition().getY()), (int) (tileSize * 0.9), (int) (tileSize * 0.9));
					g.setColor(Color.WHITE);
					g.setFont(new Font("Consolas", Font.BOLD, 16));
					g.drawString(String.valueOf(tile.getValue()), (int) (tile.getPosition().getX() + (tileSize * 0.1)), (int) (tile.getPosition().getY() + tileSize - (tileSize * 0.1)));
				}
			}
		}
	}

	public int getActualWidth() {
		int width = (int) twfe.getWidth() - 16;
		return width;
	}

	public void updateTileSize() {
		tileSize = getActualWidth() * 1.0 / twfe.getGrid().length;
	}

	public void addStartingTiles() {
		new NumberTile(twfe, 2);
		new NumberTile(twfe, ((int) (Math.random() * 2) + 1) * 2);
	}

	private PyList getGrid() {
		PyList out = new PyList();
		for (int row = 0; row < twfe.getGrid().length; row++) {
			PyList column = new PyList();
			for (int col = 0; col < twfe.getGrid().length; col++) {
				if (twfe.getGrid()[row][col] == null) {
					column.__add__(new PyInteger(0));
				} else
					column.__add__(twfe.getGrid()[row][col].getPyInt());
			}
			out.__add__(column);
		}
		return out;
	}

	private class MoveInfo {

		private int			start, end;
		private NumberTile	tile;
		private int			axis;
		private static final int	VERTICAL	= 0, HORIZONTAL = 1;

		private MoveInfo(NumberTile tile, int axis, int start, int end) {
			this.tile = tile;
			this.start = start;
			this.end = end;
			this.axis = axis;
		}

		public double getIncrementAmount() {
			return (start - end) * tileSize / ANIM_STEPS;
		}

		public NumberTile getTile() {
			return tile;
		}

		public int getAxis() {
			return axis;
		}

	}
}
