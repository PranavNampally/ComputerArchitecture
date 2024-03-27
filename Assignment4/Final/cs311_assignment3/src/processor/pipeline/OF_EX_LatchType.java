package processor.pipeline;

import generic.Instruction;

public class OF_EX_LatchType{
	
	boolean EX_enable;
	Instruction instruction;
	int op1=-1;
	int op2=-1;
	int imm=-1;
	int branchTarget=-1;
	boolean isNOP;

	public OF_EX_LatchType(){
		this.EX_enable = true;
		this.isNOP=false;
	}
	public boolean isEX_enable() {
		return EX_enable;
	}
	public void setEX_enable(boolean eX_enable) {
		this.EX_enable = eX_enable;
	}
	public void setInstruction(Instruction inst){
		this.instruction=inst;
	}
	public Instruction getInstruction(){
		return instruction;
	}
	public int getOp1(){
		return op1;
	}
	public void setOp1(int o1){
		this.op1=o1;
	}
	public int getOp2(){
		return op2;
	}
	public void setOp2(int o2){
		this.op2=o2;
	}
	public int getImm(){
		return imm;
	}
	public void setImm(int im){
		this.imm=im;
	}
	public int getbranchTarget(){
		return branchTarget;
	}
	public void setbranchTarget(int bt){
		this.branchTarget=bt;
	}
	public void setisNOP(boolean np) {
		this.isNOP=np;
	}
	public boolean getisNOP(){
		return isNOP;
	}
}
