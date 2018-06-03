package btimer;

import java.util.Timer;
import java.util.TimerTask;

public class Counter {
	private int counterType;
	private Integer[] times = {0,0,0};
	private long TTime;
	private boolean running;
	
	Timer timer = new Timer();
	TimerTask task = new TimerTask() {public void run() {}};
	Counter current = null;
	
	public Counter() {}
	public Counter(String TInput) {
		counterType = TInput.split(":", -1).length - 1;
		switch(counterType) {
		case 0:
			TTime = Integer.parseInt(TInput.split(":")[0]);
			times[0] = (int) TTime;
			break;
		case 1:
			TTime += Integer.parseInt(TInput.split(":")[1]);
			TTime += Integer.parseInt(TInput.split(":")[0])*60;
			break;
		case 2:
			TTime += Integer.parseInt(TInput.split(":")[2]);
			TTime += Integer.parseInt(TInput.split(":")[1])*60;
			TTime += Integer.parseInt(TInput.split(":")[0])*3600;
			break;
		}
		current = this;
		this.counterStart();
	}
	
	public void counterStart() {
		switch(counterType) {
		case 0:
			task = new TimerTask() {
				public void run() {
					TTime -= 1;
					times[0] = times[0]-1;
					BorderlessTimer.timeGUI.updateTimer(current);
					if(TTime == 0) {
						timer.cancel();
						BorderlessTimer.timeGUI.restartTimer();
					}
				}
			};
			break;
		case 1:
			task = new TimerTask() {
				public void run() {
					TTime -= 1;
					times[0] = (int) (TTime % 60);
					times[1] = (int) ((TTime % 3600) / 60);
					BorderlessTimer.timeGUI.updateTimer(current);
					if(TTime == 0) {
						timer.cancel();
						BorderlessTimer.timeGUI.restartTimer();
					}
				}
			};
			break;
		case 2:
			task = new TimerTask() {
				public void run() {
					TTime -= 1;
					times[0] = (int) (TTime % 60);
					times[1] = (int) ((TTime % 3600) / 60);
					times[2] = (int) (TTime / 3600);
					BorderlessTimer.timeGUI.updateTimer(current);
					if(TTime == 0) {
						timer.cancel();
						BorderlessTimer.timeGUI.restartTimer();
					}
				}
			};
			break;
		}
		running = true;
		TTime += 1;//Let me call directly to prevent displaying 00:00(with using 1000 delay before starting timer)or 1 second less than desired time
		timer.scheduleAtFixedRate(task, 0, 1000);
	}
	
	public void counterStop() {
		timer.cancel();
		timer = new Timer();
		running = false;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	String getRemaining() {
		String tempS = null, rem = null;
		switch(counterType) {
		case 0:
			tempS = times[0].toString();
			if(tempS.length() < 2) {
				tempS = "0" + tempS;
			}
			rem = tempS;
			break;
		case 1:
			tempS = times[0].toString();
			if(tempS.length() < 2) {
				tempS = "0" + tempS;
			}
			rem = tempS;
			tempS = times[1].toString();
			if(tempS.length() < 2) {
				tempS = "0" + tempS;
			}
			rem = tempS + ":" + rem;
			break;
		case 2:
			tempS = times[0].toString();
			if(tempS.length() < 2) {
				tempS = "0" + tempS;
			}
			rem = tempS;
			tempS = times[1].toString();
			if(tempS.length() < 2) {
				tempS = "0" + tempS;
			}
			rem = tempS + ":" + rem;
			if(times[2] > 0) {
				tempS = times[2].toString();
				if(tempS.length() < 2) {
					tempS = "0" + tempS;
				}
				rem = tempS + ":" + rem;
			}
			break;
		}
		return rem;
	}
}
