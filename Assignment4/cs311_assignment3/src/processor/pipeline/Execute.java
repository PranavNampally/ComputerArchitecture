package processor.pipeline;

import processor.Processor;
import generic.Instruction;
import generic.Instruction.OperationType;
public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	IF_OF_LatchType IF_OF_Latch;
	IF_EnableLatchType IF_EnableLatch;


	
	public int bin_to_sign_int(String bin) {
		while(bin.length()<32) {
			bin=bin.charAt(0)+bin;
		}
		
		long l = Long.parseLong(bin, 2);                
  		int result = (int) l;
  		//Source:https://mkyong.com/java/java-convert-negative-binary-to-integer/
		return result;
	}

	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch, IF_OF_LatchType if_of_Latch, IF_EnableLatchType if_Enable_latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.IF_OF_Latch = if_of_Latch;
		this.IF_EnableLatch =if_Enable_latch;
	}
	
	public void performEX()
	{
		
		//TODO
		if(OF_EX_Latch.isEX_enable())
		{
			if(OF_EX_Latch.getisNOP()==true) {
				OF_EX_Latch.setisNOP(false);
				EX_MA_Latch.setisNOP(true);
				OF_EX_Latch.setInstruction(null);
				return;
			}
		System.out.println("EX Stage:");
		Instruction inst=OF_EX_Latch.instruction;
		OF_EX_Latch.setInstruction(null);
		EX_MA_Latch.setInstruction(inst);
		if(inst==(null)) {
			return;
		}
		OperationType op_type=inst.getOperationType();
		OperationType[] all_operations= OperationType.values();
		int op1=0;
		int op2=0;
		int imm=0;
		long aluResult=0;
		boolean isBranchTaken=false;
		if(op_type==all_operations[0] ){//add
			op1=OF_EX_Latch.getOp1();
			op2=OF_EX_Latch.getOp2();
			aluResult=op1+op2;
		}
		else if(op_type==all_operations[2]){//sub
			op1=OF_EX_Latch.getOp1();
			op2=OF_EX_Latch.getOp2();
			aluResult=op1-op2;
		}
		else if(op_type==all_operations[4]){//mul
			op1=OF_EX_Latch.getOp1();
			op2=OF_EX_Latch.getOp2();
			aluResult=op1*op2;
		}
		else if(op_type==all_operations[6]){//div
			op1=OF_EX_Latch.getOp1();
			op2=OF_EX_Latch.getOp2();
			aluResult=op1/op2;
			RegisterFile rf= containingProcessor.getRegisterFile();
			// int int_rf=rf.getValue(31);
			rf.setValue(31,op1%op2);
		}
		else if(op_type==all_operations[8]){//and
			op1=OF_EX_Latch.getOp1();
			op2=OF_EX_Latch.getOp2();
			aluResult=op1&op2;
		}
		else if(op_type==all_operations[10]){//or
			op1=OF_EX_Latch.getOp1();
			op2=OF_EX_Latch.getOp2();
			aluResult=op1|op2;
		}
		else if(op_type==all_operations[12]){//xor
			op1=OF_EX_Latch.getOp1();
			op2=OF_EX_Latch.getOp2();
			aluResult=op1^op2;
		}
		else if(op_type==all_operations[14]){//slt
			op1=OF_EX_Latch.getOp1();
			op2=OF_EX_Latch.getOp2();
			if(op1<op2){
				aluResult=0;	
			}
			else{
				aluResult=1;
			}
		}
		else if(op_type==all_operations[16]){//sll
			op1=OF_EX_Latch.getOp1();
			op2=OF_EX_Latch.getOp2();
			aluResult=op1<<op2;
			RegisterFile rf= containingProcessor.getRegisterFile();
			String binary_op1=Integer.toBinaryString(op1);
			rf.setValue(31,bin_to_sign_int(binary_op1.substring(0, op2)));
			
		}
		else if(op_type==all_operations[18]){//srl
			op1=OF_EX_Latch.getOp1();
			op2=OF_EX_Latch.getOp2();
			aluResult=op1>>>op2;
			RegisterFile rf= containingProcessor.getRegisterFile();
			String binary_op1=Integer.toBinaryString(op1);
			rf.setValue(31,bin_to_sign_int(binary_op1.substring(binary_op1.length()-op2, binary_op1.length())));
		}
		else if(op_type==all_operations[20]){//sra
			op1=OF_EX_Latch.getOp1();
			op2=OF_EX_Latch.getOp2();
			aluResult=op1>>op2;
			RegisterFile rf= containingProcessor.getRegisterFile();
			String binary_op1=Integer.toBinaryString(op1);
			rf.setValue(31,bin_to_sign_int(binary_op1.substring(binary_op1.length()-op2, binary_op1.length())));
		}
		
		

		else if(op_type==all_operations[1]||op_type==all_operations[22] || op_type==all_operations[23]){//addi, ld, st
			op1=OF_EX_Latch.getOp1();
			imm=OF_EX_Latch.getImm();
			aluResult=op1+imm;
		}
		else if(op_type==all_operations[3]){//subi
			op1=OF_EX_Latch.getOp1();
			imm=OF_EX_Latch.getImm();
			aluResult=op1-imm;
		}
		else if(op_type==all_operations[5]){//muli
			op1=OF_EX_Latch.getOp1();
			imm=OF_EX_Latch.getImm();
			aluResult=op1*imm;
		}
		else if(op_type==all_operations[7]){//divi
			op1=OF_EX_Latch.getOp1();
			imm=OF_EX_Latch.getImm();
			aluResult=op1/imm;
			RegisterFile rf= containingProcessor.getRegisterFile();
			// int int_rf=rf.setValue(31);
			rf.setValue(31,op1%imm);
			//SET the remainder in x31
		}
		else if(op_type==all_operations[9]){//andi
			op1=OF_EX_Latch.getOp1();
			imm=OF_EX_Latch.getImm();
			aluResult=op1&imm;
		}
		else if(op_type==all_operations[11]){//ori
			op1=OF_EX_Latch.getOp1();
			imm=OF_EX_Latch.getImm();
			aluResult=op1|imm;
		}
		else if(op_type==all_operations[13]){//xori
			op1=OF_EX_Latch.getOp1();
			imm=OF_EX_Latch.getImm();
			aluResult=op1^imm;
		}
		else if(op_type==all_operations[15]){//slti
			op1=OF_EX_Latch.getOp1();
			imm=OF_EX_Latch.getImm();
			if(op1<imm){
				aluResult=0;	
			}
			else{
				aluResult=1;
			}
		}
		else if(op_type==all_operations[17]){//slli
			op1=OF_EX_Latch.getOp1();
			imm=OF_EX_Latch.getImm();
			aluResult=op1<<imm;
			RegisterFile rf= containingProcessor.getRegisterFile();
			String binary_op1=Integer.toBinaryString(op1);
			rf.setValue(31,bin_to_sign_int(binary_op1.substring(0, imm)));
		}
		else if(op_type==all_operations[19]){//srli
			op1=OF_EX_Latch.getOp1();
			imm=OF_EX_Latch.getImm();
			aluResult=op1>>>imm;
			RegisterFile rf= containingProcessor.getRegisterFile();
			String binary_op1=Integer.toBinaryString(op1);
			rf.setValue(31,bin_to_sign_int(binary_op1.substring(binary_op1.length()-imm, binary_op1.length())));
		}
		else if(op_type==all_operations[21]){//srai
			op1=OF_EX_Latch.getOp1();
			imm=OF_EX_Latch.getImm();
			aluResult=op1>>imm;
			RegisterFile rf= containingProcessor.getRegisterFile();
			String binary_op1=Integer.toBinaryString(op1);
			rf.setValue(31,bin_to_sign_int(binary_op1.substring(binary_op1.length()-imm, binary_op1.length())));
		}


		else if(op_type==all_operations[24]){//jmp
			//jmp
			EX_IF_Latch.setbranchTarget(OF_EX_Latch.getbranchTarget());
			EX_IF_Latch.setIsBranchTarget(true);
		}
		else if(op_type==all_operations[25]){//beq
			op1=OF_EX_Latch.getOp1();
			op2=OF_EX_Latch.getOp2();
			if(op1==op2){
				isBranchTaken=true;
			}
			EX_IF_Latch.setbranchTarget(OF_EX_Latch.getbranchTarget());
			EX_IF_Latch.setIsBranchTarget(isBranchTaken);

		}
		else if(op_type==all_operations[26]){//bne
			op1=OF_EX_Latch.getOp1();
			op2=OF_EX_Latch.getOp2();
			if(op1!=op2){
				isBranchTaken=true;
			}
			EX_IF_Latch.setbranchTarget(OF_EX_Latch.getbranchTarget());
			EX_IF_Latch.setIsBranchTarget(isBranchTaken);

		}
		else if(op_type==all_operations[27]){//blt
			op1=OF_EX_Latch.getOp1();
			op2=OF_EX_Latch.getOp2();
			if(op1<op2){
				isBranchTaken=true;
			}
			EX_IF_Latch.setbranchTarget(OF_EX_Latch.getbranchTarget());
			EX_IF_Latch.setIsBranchTarget(isBranchTaken);

		}
		else if(op_type==all_operations[28]){//bgt
			op1=OF_EX_Latch.getOp1();
			op2=OF_EX_Latch.getOp2();
			if(op1>op2){
				isBranchTaken=true;
			}
			EX_IF_Latch.setbranchTarget(OF_EX_Latch.getbranchTarget());
			EX_IF_Latch.setIsBranchTarget(isBranchTaken);

		}
		if ((aluResult > Integer.MAX_VALUE) || (aluResult < Integer.MIN_VALUE)){
			String binary_alu=Long.toBinaryString(aluResult);
			while(binary_alu.length()<64) {
				binary_alu=binary_alu.charAt(0)+binary_alu;
			}
			RegisterFile rf= containingProcessor.getRegisterFile();
			rf.setValue(31,bin_to_sign_int(binary_alu.substring(0, 32)));
		}
		
		EX_MA_Latch.setAluResult((int)aluResult);
		op2=OF_EX_Latch.getOp2();
		EX_MA_Latch.setOp2(op2);
		System.out.println(EX_MA_Latch.getOp2()+" "+EX_MA_Latch.getAluResult()+" "+EX_MA_Latch.getInstruction()+" "+EX_IF_Latch.getbranchTarget()+" "+EX_IF_Latch.getIsBranchTaken());
		EX_MA_Latch.setMA_enable(true);
		
		IF_EnableLatch.setIF_enable(true);
		if(op_type==all_operations[29]) {
			IF_OF_Latch.setOF_enable(false);
			IF_EnableLatch.setIF_enable(false);
		}
		if(EX_IF_Latch.getIsBranchTaken()) {
			OF_EX_Latch.setInstruction(null);
			OF_EX_Latch.setisNOP(true);
			IF_OF_Latch.setOF_enable(false);
			IF_OF_Latch.setInstruction(-1);
			IF_EnableLatch.setIF_enable(false);
		}
		
		}
	}


}
