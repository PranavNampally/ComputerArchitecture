package processor.pipeline;
import generic.Instruction;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	int ALU_Result;
	int op2;
	Instruction instruction;

	public EX_MA_LatchType()
	{
		MA_enable = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}

	public void setInstruction(Instruction inst) {
		this.instruction=inst;
	}
	public Instruction getInstruction(){
		return instruction;
	}

	public void setAluResult(int alu) {
		this.ALU_Result=alu;
	}
	public int getAluResult(){
		return ALU_Result;
	}

	public void setOp2(int o2) {
		this.op2=o2;
	}
	public int getOp2(){
		return op2;
	}



}
