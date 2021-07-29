stage('test staging'){
    when{
	anyOf{
	branch 'feature/first_task';
	branch 'feature/second_task';
	branch 'feature/third_task';
	branch 'feature/fourth_task';
	}
	steps{
	mvn clean compile test
	}
}