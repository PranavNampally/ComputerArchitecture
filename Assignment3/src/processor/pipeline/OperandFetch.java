package processor.pipeline;

import processor.Processor;
import generic.Instruction;
import generic.Instruction.OperationType;
import generic.Operand;
import generic.Operand.OperandType;

import generic.Simulator;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	
	public int bin_to_sign_int(String bin) {
		while(bin.length()<32) {
			bin=bin.charAt(0)+bin;
		}
		
		long l = Long.parseLong(bin, 2);                
  		int result = (int) l;
  		//Source:https://mkyong.com/java/java-convert-negative-binary-to-integer/
		return result;
	}
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			System.out.println("OF Stage:");
			
			//TODO
			int signed_inst=IF_OF_Latch.getInstruction();
			String binary_inst=Integer.toBinaryString(signed_inst);
			if(signed_inst>=0) {
				binary_inst='0'+binary_inst;
			}
			while(binary_inst.length()<32){
				binary_inst= binary_inst.charAt(0)+binary_inst;
			}
//			System.out.println(signed_inst+" "+binary_inst);
//			System.out.println(signed_inst+" "+binary_inst.length());
			
			String binary_opcode=binary_inst.substring(0,5);
			int int_opcode=Integer.parseInt(binary_opcode, 2);
			Instruction instruction=new Instruction();
			OperationType[] all_operations= OperationType.values(); 
			OperationType operationType=all_operations[int_opcode];
			instruction.setOperationType(operationType);
			instruction.setProgramCounter(containingProcessor.getRegisterFile().getProgramCounter());
			Operand rs1=new Operand();
			rs1.setOperandType(OperandType.Register);

			Operand rs2=new Operand();
			rs2.setOperandType(OperandType.Register);

			Operand rd=new Operand();
			rd.setOperandType(OperandType.Register);

			Operand imm=new Operand();
			imm.setOperandType(OperandType.Immediate);

			Operand lab=new Operand();
			lab.setOperandType(OperandType.Label);

			String binary_rs1=new String();
			String binary_rs2=new String();
			String binary_rd=new String();
			String binary_imm=new String();
			int op1, op2, int_rd, int_rs1, int_rs2, int_imm, int_bt;
			switch(int_opcode){
				case 0:
				case 2:
				case 4:
				case 6:
				case 8:
				case 10:
				case 12:
				case 14:
				case 16:
				case 18:
				case 20:
					//R3
					binary_rs1=binary_inst.substring(5, 10);
					int_rs1=Integer.parseInt(binary_rs1,2);
					rs1.setValue(int_rs1);
					op1=containingProcessor.getRegisterFile().getValue(int_rs1);
					instruction.setSourceOperand1(rs1);

					binary_rs2=binary_inst.substring(10, 15);
					int_rs2=Integer.parseInt(binary_rs2,2);
					rs2.setValue(int_rs2);
					op2=containingProcessor.getRegisterFile().getValue(int_rs2);
					instruction.setSourceOperand2(rs2);

					binary_rd=binary_inst.substring(15, 20);
					int_rd=Integer.parseInt(binary_rd,2);
					rd.setValue(int_rd);
					instruction.setDestinationOperand(rd);
					
					OF_EX_Latch.setInstruction(instruction);
					OF_EX_Latch.setOp1(op1);
					OF_EX_Latch.setOp2(op2);
					
//					System.out.println(OF_EX_Latch.getOp1()+" "+OF_EX_Latch.getOp2()+" "+OF_EX_Latch.getInstruction());
					break;				


				case 1:
				case 3:
				case 5:
				case 7:
				case 9:
				case 11:
				case 13:
				case 15:
				case 17:
				case 19:
				case 21:
				case 22:
				//R2I
					binary_rs1=binary_inst.substring(5, 10);
					int_rs1=Integer.parseInt(binary_rs1,2);
					rs1.setValue(int_rs1);
					op1=containingProcessor.getRegisterFile().getValue(int_rs1);
					instruction.setSourceOperand1(rs1);
					
					binary_rd=binary_inst.substring(10, 15);
					int_rd=Integer.parseInt(binary_rd,2);
					rd.setValue(int_rd);
					instruction.setDestinationOperand(rd);

					binary_imm=binary_inst.substring(15, 32);
					int_imm=bin_to_sign_int(binary_imm);
//					System.out.println(int_imm);
					imm.setValue(int_imm);
					instruction.setSourceOperand2(imm);
					
					OF_EX_Latch.setInstruction(instruction);
					OF_EX_Latch.setOp1(op1);
					OF_EX_Latch.setImm(int_imm);
//					System.out.println(OF_EX_Latch.getOp1()+" "+OF_EX_Latch.getImm()+" "+OF_EX_Latch.getInstruction());
					break;
				
				case 23:
				//st
					binary_rs1=binary_inst.substring(5, 10);
					int_rs1=Integer.parseInt(binary_rs1,2);
					rs1.setValue(int_rs1);
					op2=containingProcessor.getRegisterFile().getValue(int_rs1);
					instruction.setSourceOperand1(rs1);
					
					binary_rd=binary_inst.substring(10, 15);
					int_rd=Integer.parseInt(binary_rd,2);
					rd.setValue(int_rd);
					op1=containingProcessor.getRegisterFile().getValue(int_rd);
					instruction.setDestinationOperand(rd);

					binary_imm=binary_inst.substring(15, 32);
					int_imm=bin_to_sign_int(binary_imm);
					imm.setValue(int_imm);
					instruction.setSourceOperand2(imm);

					OF_EX_Latch.setInstruction(instruction);
					OF_EX_Latch.setOp1(op1);
					OF_EX_Latch.setOp2(op2);
					OF_EX_Latch.setImm(int_imm);
//					System.out.println(OF_EX_Latch.getOp1()+" "+OF_EX_Latch.getImm()+" "+OF_EX_Latch.getInstruction());
					break;

				case 24:
				//jmp
					binary_rd=binary_inst.substring(5, 10);
					int_rd=Integer.parseInt(binary_rd,2);
					rd.setValue(int_rd);
					instruction.setDestinationOperand(rd);

					binary_imm=binary_inst.substring(10, 32);
					int_imm=bin_to_sign_int(binary_imm);
					imm.setValue(int_imm);
					instruction.setSourceOperand2(imm);

					int_imm*=1;
					int_bt=int_imm+containingProcessor.getRegisterFile().getProgramCounter()-1;
					OF_EX_Latch.setInstruction(instruction);
					OF_EX_Latch.setImm(int_imm);
					OF_EX_Latch.setbranchTarget(int_bt);
//					System.out.println(OF_EX_Latch.getbranchTarget()+" "+OF_EX_Latch.getImm()+" "+OF_EX_Latch.getInstruction());
					break;
				
				case 25:
				case 26:
				case 27:
				case 28:
					binary_rs1=binary_inst.substring(5, 10);
					int_rs1=Integer.parseInt(binary_rs1,2);
					rs1.setValue(int_rs1);
					op1=containingProcessor.getRegisterFile().getValue(int_rs1);
					instruction.setSourceOperand1(rs1);

					binary_rs2=binary_inst.substring(10, 15);
					int_rs2=Integer.parseInt(binary_rs2,2);
					rs2.setValue(int_rs2);
					op2=containingProcessor.getRegisterFile().getValue(int_rs2);
					instruction.setSourceOperand2(rs2);

					binary_imm=binary_inst.substring(15, 32);
					int_imm=bin_to_sign_int(binary_imm);
					System.out.println(binary_imm+" "+int_imm);
					imm.setValue(int_imm);
					instruction.setDestinationOperand(imm);
					
					int_imm*=1;
					int_bt=int_imm+containingProcessor.getRegisterFile().getProgramCounter()-1;
					// System.out.println(containingProcessor.getRegisterFile().getProgramCounter());
					OF_EX_Latch.setInstruction(instruction);
					OF_EX_Latch.setOp1(op1);
					OF_EX_Latch.setOp2(op2);
					OF_EX_Latch.setImm(int_imm);
					OF_EX_Latch.setbranchTarget(int_bt);

//					System.out.println(OF_EX_Latch.getOp1()+" "+OF_EX_Latch.getOp2()+" "+OF_EX_Latch.getbranchTarget()+" "+OF_EX_Latch.getImm()+" "+OF_EX_Latch.getInstruction());
					break;
				
				case 29:
					Simulator.setSimulationComplete(true);
					return;

				default:
					break;
			}
			System.out.println(OF_EX_Latch.getOp1()+" "+OF_EX_Latch.getOp2()+" "+OF_EX_Latch.getImm()+" "+OF_EX_Latch.getbranchTarget()+" "+OF_EX_Latch.getInstruction());
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}

}
                                                                                                    
// `...```                                                      
// ``.-:/++oo+oooo++osyso+:.`                                                 
// `.-/+oo++/:-..```````--...``.:+oo++++++//-.`                                     
// `-/+o+/-.```      ````...`         ``...--:::/+o+-`                                  
// .-/oo+:.....`   `.`   `````            .---.---.`  `-+o+.                                
// `.:oo/:.`  ``.--//-.``     ``..```          ````          ./o:.`                             
// .:+o+-..` ./+/::-::-.         ````.:-.```.....```             `-oo.                            
// `+s/-` ``.`.oo.`                     `.::://:.....--.`            `/+---.``                      
// .so.       .++:-.``   `.--:--::.        `-/:-`      `.-`              `.:+o/.                     
// `/y/`  `-/-` `````.:::///:.`             .+.`       ````                     `--`                   
// /y--:..+/.                      `.-::-```--   ```..-.---::-`        ``.`       .::-                 
// y.-/-.+-                      `::.....        -+.`        .`          `.---. `-:..+o`               
// .`+.      ``                 `-.    `:         :-                         `-+/.`  `+y.              
// :` `..----``-/`         . `..     `--                   ``       `        .o-    `/y/`            
// -/`..```-/-`.::`        .:           .`      `.           .::.              :+.--``:.ss`           
// .` `  ``.               -:          .-`      `:-           ``--.`        `..-//:/``: `so``         
// -- `             .` ./`       `//:        `o.            ``..`             `` ..  `so`         
// `o-.              .` `/:      `o:           .`                                 .s-  .s+`        
// -o.s`       ``    .   `o.     -y`     :`       ``  ``                           -o` `.s+        
// `.-/-`/:`   ./+/:`        +:     `o`     :s`     `+.  `.             `:            `+``: .h:       
// ---.`  `-:-..o+`      ./   ``             .y`     :+    `    .``      -o            `+./:  :h.      
// .        ``.:+-       +/   `              ./`     /:   `    `:/++.  ``/-             -++`   so      
// `.  .o.      ```-/:`                      `  `o.      `/+-:-:.               ..`   .h-     
// ``   `o:   :+-`       `                         `` `+:       :/.``                   ..   oo     
// `/-   .+`    `.`                     `.``       .:/-``       -o` `:.                       -h-    
// /-    `::          .`          `--:----.`                  `//.   :o`                   `  `so    
// `+.      -:.        -+.                                      `     `/`               `` `:`  /y    
// `+:       `.-     ` `:/                                -.         ./.                `-  -.  /h`   
// `+/                  --      `.----.                  -+`       `--`                 --  `-` .h-   
// `-+`                             ```:/.`````````      `/-       `.`                  `:`   `.-`os`  
// :.`       `...`        ``````        .:/://:------.`   .`                            .`      .:/y`  
// `..-:::....-:::---::.        `-//:`     `.:-`                                        :ss`  
// `.-:::-.`              ./-.`         `.                      ``````````         -h/   
//    `.-..` ``/.      ``....``                      ...........`````.` `s`   
// .                   .:--:::.  `.   `-.....`..-::.                 ```.--.......-...--``  o`   
// /`   `:                  -:.   `:`       `//`        `.`                ````......`     `....` .+`  
// //.  `:               ```.      :.        `:`                             `-...```-`    `.  `-` /-  
// -     -`               ```      `:`                            `::.   `../sos+o+//o+/-  `-`--::--/  
// :      .`                        ``                            +/++++ooohd+`s:mMd:/`-do++osomNyoyy. 
// y+.``.```-...                                                `oy-/+.`   `/o/:///::::so.`/y/:sso-so` 
// ./oso:::-:+..                                    ``         -/:y//:` ``   `-://::::-.   o++/:/+/-+. 
// `:so:.`//-.`                         ``    `:/-`      `-os-`/s//`-+.                 ::.````  :- 
// `-/+:`-/+s+.          ``       `.://::::/oo..`     .+s/`   :s--//-          `-.     .o`    `+. 
// ./s+.`   ``......`  `..`.:/-.`...` .--.-/os+.      o.. `o`         `+-`.. .:/`    /-  
// ./sso/:-.``             `-.` ``.-:/os+:-`        +`   -+`         ..``` .-`   `+-   
// `-+oso++++:-..........--:/+ooo+/:-`            +`   --::`     `-...````.`  `+-    
// ``....:/+oooooooooo++/:-.`                 `+`   -. .::.`  `..------:-  ::     
// 									   /-    -`   `:/.            `:/`     
// 									  .o`    `      `:/-`       `-+.       
// 									 .o`   -/-         .:::::+:::.         
// 								  `.+/`  `:/`           `:`  //:...```     

