Input,un  fisier xml cu o structura de 3 nivele cum e descris mai jos
 
   <project>
        <source name="src">
                <package name="com.test">
                    <class name="MyClass" />
                </package>
                
                <package name="com.test.exec">
                      <class>
                            ClassExec
                    </class>
                    <class>
                            ClassExec2
                    </class>
                </package>
        </source>


        <source name="source">
                <package name="my.pack">
                    <class name="TestPack" />
                </package>
                
                <package name="my.package1">
                      <class>
                            MyServlet
                    </class>
                    <class>
                            MyServlet2
                    </class>
                </package>
        </source>        
            
        
   
   <project>
   
   
   De creat o componenta Windows UI Tree, care ar primi acest xml ca datasource ,
  si ar arata cam asa
 
 
 

   
   Librariile recomandate (jface, swt), https://wiki.eclipse.org/JFace
Mediu de lucru eclipse
Java version 8

