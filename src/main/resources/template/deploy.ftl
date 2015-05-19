
:pack xxx 打包
cd /d ${codePath}
svn up

cmd /c mvn clean package -Dmaven.test.skip=true

:删除旧的war包和文件夹
cd /d ${tomcatPath}\webapps
rd /q /s ${warName}
del ${warName}.war

:将打好的包放进去
cd /d ${codePath}
copy target\${warName}.war "${tomcatPath}\webapps\${warName}.war"

cd /d "${tomcatPath}\bin"
startup.bat

pause
