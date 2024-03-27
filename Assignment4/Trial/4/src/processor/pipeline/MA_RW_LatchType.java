package processor.pipeline;
import generic.Instruction;


public class MA_RW_LatchType {
	
	boolean RW_enable;
	int ALU_Result;
	int ld_Result;
	Instruction instruction;
	boolean isNOP;


	public MA_RW_LatchType()
	{
		this.RW_enable = true;
		this.isNOP =false;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		this.RW_enable = rW_enable;
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

	
	public void setldResult(int lr) {
		this.ld_Result=lr;
	}
	public int getldResult(){
		return ld_Result;
	}
	
	public void setisNOP(boolean np) {
		this.isNOP=np;
	}
	public boolean getisNOP(){
		return isNOP;
	}
	
}
