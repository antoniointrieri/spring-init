# SPRING BOOT INIT PROJECT - ROOT

## How to hot swap spring applications:

- add the maven dependency
	<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>

- Go to File > Settings > Build,Execution, Deployment > Compiler
	and enable 'Build Project automatically'

- Press 'Ctrl+Shift+A' and search for 'Registry'
	and then enable 'compiler.automake.allow.when.app.running'

- Restart IDE

## Run Configurations

        VM options          -> -Dspring.profiles.active=develop 
        Program argument    -> -Pdevelop
