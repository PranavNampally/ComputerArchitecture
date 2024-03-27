package generic;

import java.io.FileInputStream;
import generic.Operand.OperandType;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.nio.ByteBuffer;

public class Simulator {
		
	static FileInputStream inputcodeStream = null;
	
	public static void setupSimulation(String assemblyProgramFile)
	{	
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}
	
	public static void assemble(String objectProgramFile)
	{
		//TODO your assembler code	
		try(
			//1. open the objectProgramFile in binary mode
			FileOutputStream output_file=new FileOutputStream(objectProgramFile);
			BufferedOutputStream buffer_file = new BufferedOutputStream(output_file);
		){
			//2. write the firstCodeAddress to the file
			int firstCode=ParsedProgram.firstCodeAddress;
			byte[] firstCodew = ByteBuffer.allocate(4).putInt(firstCode).array();
			buffer_file.write(firstCodew);
//			System.out.println(firstCodew);
			
			for(int i=0; i<ParsedProgram.data.size(); i++) {
				//3. write the data to the file
				int temp=ParsedProgram.data.get(i);
				byte[] tempw = ByteBuffer.allocate(4).putInt(temp).array();
				buffer_file.write(tempw);
			}
			
			String [] all_inst= {"add", "addi", "sub", "subi", "mul", "muli", "div", "divi", "and", "andi", "or", "ori", "xor", "xori", "slt", "slti", "sll", "slli", "srl", "srli", "sra", "srai", "load", "store", "jmp", "beq", "bne", "blt", "bgt", "end"};
			//4. assemble one instruction at a time, and write to the file
			for(int i=0; i<ParsedProgram.code.size();i++) {
				Instruction current_inst=ParsedProgram.code.get(i);
//				System.out.println(current_inst);
				int inst_index=Arrays.asList(all_inst).indexOf(String.valueOf(current_inst.operationType));
//				System.out.println(inst_index);
				
				String opcode=String.format("%5s",Integer.toBinaryString(inst_index)).replaceAll(" ", "0");
				String binary_op;
				long long_num;
				int num;
				byte[] numw;
				String rs1, rs2, rd, imm, zeroes;
				int progc= current_inst.getProgramCounter();
//				int jump= ParsedProgram.symtab.get(current_inst.getDestinationOperand().getLabelValue())-progc;
				int x;
				switch(inst_index) {
				
				case 0: 
					//add
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					rs2=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rs2+rd+"000000000000";
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
				
				
				
				case 1:
					//addi - R2I
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					imm=String.format("%" + 17 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rd+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
				
				case 2:
					//sub
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					rs2=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rs2+rd+"000000000000";
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
					
					
				case 3:
					//subi - R2I
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					imm=String.format("%" + 17 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rd+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
				
				case 4:
					//mul
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					rs2=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rs2+rd+"000000000000";
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
					
				case 5:
					//muli - R2I
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					imm=String.format("%" + 17 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rd+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);	
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
				
				case 6:
					//div
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					rs2=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rs2+rd+"000000000000";
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
					
					
				case 7:
					//divi - R2I
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					imm=String.format("%" + 17 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rd+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
				
				case 8:
					//and
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					rs2=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rs2+rd+"000000000000";
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
					
				case 9:
					//addi - R2I
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					imm=String.format("%" + 17 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rd+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
				
				case 10:
					//or
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					rs2=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rs2+rd+"000000000000";
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
					
				case 11:
					//ori - R2I
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					imm=String.format("%" + 17 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rd+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
				case 12:
					//xor
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					rs2=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rs2+rd+"000000000000";
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
					
				case 13:
					//xori - R2I
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					imm=String.format("%" + 17 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rd+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
				
				case 14:
					//slt
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					rs2=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rs2+rd+"000000000000";
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
					
				case 15:
					//slti - R2I
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					imm=String.format("%" + 17 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rd+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
				
				case 16:
					//sll
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					rs2=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rs2+rd+"000000000000";
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
					
				case 17:
					//slli - R2I
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					imm=String.format("%" + 17 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rd+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
				
				case 18:
					//srl
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					rs2=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rs2+rd+"000000000000";
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
					
				case 19:
					//srli - R2I
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					imm=String.format("%" + 17 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rd+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
				
				case 20:
					//sra
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					rs2=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rs2+rd+"000000000000";
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
					
				case 21:
					//srai - R2I
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					imm=String.format("%" + 17 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rd+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);		
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
				
				case 22:
					//load - R2I
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					imm=String.format("%" + 17 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rd+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
					
				case 23:
					//store - R2I
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rd=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getDestinationOperand().value)).replaceAll(" ", "0");
					imm=String.format("%" + 17 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					binary_op=opcode+rs1+rd+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
					
				case 24:
					//jmp RI
//					rd="00000";
//					int x=Integer.parseInt (String.format("%" + 5 + "s",	Integer.toBinaryString(ParsedProgram.symtab.get(current_inst.getDestinationOperand().getLabelValue()))).replaceAll(" ", "0"),2);
//					x=x-progc;
//					imm=String.format("%" + 22 + "s",	Integer.toBinaryString(x)).replaceAll(" ", "0");
//					
//					binary_op=opcode+rd+imm;
//					
//					long_num=Long.parseLong(binary_op,2);	 //long value of the number
//					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
//					
//					numw = ByteBuffer.allocate(4).putInt(num).array();
//					buffer_file.write(numw);
//					break;
					//jmp RI
					rd="00000";
					x=ParsedProgram.symtab.get(current_inst.getDestinationOperand().getLabelValue());
					x=x-progc;
					imm=Integer.toBinaryString(x);
					if(x>=0) {
						imm=String.format("%" + 22 + "s", imm).replaceAll(" ", "0");
					}else {
						imm= imm.substring(imm.length()-22);
					}
					
					
					binary_op=opcode+rd+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
					
				case 25:
					//beq - R2I
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rs2=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					x=ParsedProgram.symtab.get(current_inst.getDestinationOperand().getLabelValue());
					x=x-progc;
					imm=Integer.toBinaryString(x);
					if(x>=0) {
						imm=String.format("%" + 17 + "s", imm).replaceAll(" ", "0");
					}else {
						imm= imm.substring(imm.length()-17);
					}
					
//					System.out.println(imm);
					binary_op=opcode+rs1+rs2+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
					
				case 26:
					//bne - R2I
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rs2=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					x=ParsedProgram.symtab.get(current_inst.getDestinationOperand().getLabelValue());
					x=x-progc;
					imm=Integer.toBinaryString(x);
					if(x>=0) {
						imm=String.format("%" + 17 + "s", imm).replaceAll(" ", "0");
					}else {
						imm= imm.substring(imm.length()-17);
					}
					
//					System.out.println(imm);
					binary_op=opcode+rs1+rs2+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
				
				case 27:
					//blt - R2I
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rs2=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					x=ParsedProgram.symtab.get(current_inst.getDestinationOperand().getLabelValue());
					x=x-progc;
					imm=Integer.toBinaryString(x);
					if(x>=0) {
						imm=String.format("%" + 17 + "s", imm).replaceAll(" ", "0");
					}else {
						imm= imm.substring(imm.length()-17);
					}
					
//					System.out.println(imm);
					binary_op=opcode+rs1+rs2+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
					
				case 28:
					//bgt - R2I
					rs1=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand1().value)).replaceAll(" ", "0");
					rs2=String.format("%" + 5 + "s",	Integer.toBinaryString(current_inst.getSourceOperand2().value)).replaceAll(" ", "0");
					x=ParsedProgram.symtab.get(current_inst.getDestinationOperand().getLabelValue());
					x=x-progc;
					imm=Integer.toBinaryString(x);
					if(x>=0) {
						imm=String.format("%" + 17 + "s", imm).replaceAll(" ", "0");
					}else {
						imm= imm.substring(imm.length()-17);
					}
					
//					System.out.println(imm);
					binary_op=opcode+rs1+rs2+imm;
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(binary_op);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
					
				case 29:
					//end - RI
					zeroes="000000000000000000000000000";
					binary_op=opcode+zeroes;
//					System.out.println(binary_op);
					
					long_num=Long.parseLong(binary_op,2);	 //long value of the number
					num=(int) long_num; 					 //typecasting to signed int having 32 bits
//					System.out.println(num);
					
					numw = ByteBuffer.allocate(4).putInt(num).array();
					buffer_file.write(numw);
					break;
				
				default:
					System.out.println("Invalid instruction");
					break;
				}
			}
			//5. close the file
			buffer_file.close();
		}
		catch(IOException error){
			error.printStackTrace();
		}	
	}	
}