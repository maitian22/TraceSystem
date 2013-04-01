#/bin/bash

find -name "*java" > 1.txt
#find -name "*.java" > 1.txt
for i in `cat 1.txt`
do
mv $i ${i/java/.java}
#mv $i ${i/.java/.tmp}
done

#find -name "*.tmp2" > 2.txt
#for i in `cat 2.txt`
#do
#mv $i ${i/.tmp2/.cpp}
#mv $i ${i/.cpp/.tmp2}
#done

