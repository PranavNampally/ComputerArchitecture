package processor.pipeline;

public class IF_OF_LatchType {
	
	boolean OF_enable;
	int instruction;
	
	public if_of_latchtype(boolean of_enable) {
		of_enable = of_enable;
	}
	
	public if_of_latchtype(boolean of_enable, int inst) {
		of_enable = of_enable;
		instruction = inst;
	}
	
	public IF_OF_LatchType() {
		OF_enable = false;
	}

	public void setOF_enable(boolean oF_enable) {
		OF_enable = oF_enable;
	}

	public boolean isOF_enable() {
		return OF_enable;
	}

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}

}
