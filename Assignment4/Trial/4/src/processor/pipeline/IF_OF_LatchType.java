package processor.pipeline;

public class IF_OF_LatchType {
	
	boolean OF_enable;
	int instruction=-1;
	
	public IF_OF_LatchType()
	{
		this.OF_enable = true;
	}

	public boolean isOF_enable() {
		return OF_enable;
	}

	public void setOF_enable(boolean oF_enable) {
		this.OF_enable = oF_enable;
	}

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}

}
