abstract class Employee (
    val firstName: String,
    val secondName: String,
    val baseSalary: Double,
    val experience: Int,
    var manager: Manager ? = null
) {
    open fun calcSalary () : Double {
        var salary:Double
        if (experience > 5) {
            salary =  baseSalary*1.2 + 500
        } else if (experience > 2) {
            salary =  baseSalary + 200
        } else {
            salary =  baseSalary
        }
        return salary;
    }


    fun giveSalary () {
        println("$firstName $secondName got salary: ${ Math.round(calcSalary()).toDouble() }")
    }


    fun assignToManager(assignedManager : Manager) {
        manager = assignedManager
        assignedManager.team.add(this)
    }
}


class Developer (
    firstName: String,
    secondName: String,
    baseSalary: Double,
    experience: Int
) : Employee (firstName, secondName, baseSalary, experience)  {


}


class Designer (
    firstName: String,
    lastName: String,
    baseSalary: Double,
    experience: Int,
    var effCoeff : Double
) : Employee (firstName, lastName, baseSalary, experience)  {
    override fun calcSalary() : Double {
        return super.calcSalary() * effCoeff
    }
}






class Manager (
    firstName: String,
    lastName: String,
    baseSalary: Double,
    experience: Int,
    var team : MutableList<Employee> = mutableListOf()
) : Employee (firstName, lastName, baseSalary, experience)  {
    override fun calcSalary() : Double {
        var salary = super.calcSalary()
        val teamNum = team.count()
        val devNum = team.filterIsInstance<Developer>().count()
        if (teamNum > 10) {
            salary = salary + 300
        } else if (teamNum > 5) {
            salary = salary + 200
        }
        if (devNum > teamNum / 2) {
            salary = salary * 1.1
        }
        return salary;
    }

    fun addEmployee (employee : Employee) {
        team.add(employee)
        employee.manager = this
    }

}



    class Department (
        var managers : MutableList<Manager> = mutableListOf()
    ) {


        fun addManager(manager: Manager) {
            managers.add(manager)
        }


        fun giveAllSalaries() {
            managers.forEach{
                    manager ->
                manager.giveSalary()
                manager.team.forEach{
                        employee ->  employee.giveSalary()
                }
            }
        }
    }






    fun main () {
        var Alex =  Developer("Alex","Smith",1500.00, 1);
        var Anthony =  Developer("Anthony", "Jones",1600.00, 2);
        var Brandon =  Developer("Brandon","Williams",1500.00, 3)
        var David: Developer =  Developer("David","Brown",1600.00, 4);
        var Hanna =  Developer("Hanna","Ivanova",1500.00, 5);
        var Fred =  Developer("Fred","Taylor",1600.00, 6);
        var Josh =  Developer("Josh","Davies",1500.00, 7);


        var Amelia =  Designer("Amelia","Wilson",1400.00, 2, 0.6);
        var Betty =  Designer("Betty","Evans",1400.00, 1, 0.7);
        var Zoe =  Designer("Zoe","Moore",1400.00, 4, 0.8);
        var Margaret =  Designer("Margaret","Johnson",1400.00, 6, 0.9);


        var Masha =  Manager("Masha","Shef",2200.00, 7);
        var Leon =  Manager("Leon","Shef",2200.00, 4);


        Anthony.assignToManager(Masha)
        Brandon.assignToManager(Masha)
        Margaret.assignToManager(Masha)
        Betty.assignToManager(Masha)
        David.assignToManager(Masha)
        Alex.assignToManager(Masha)
        Hanna.assignToManager(Masha)


        Fred.assignToManager(Leon)
        Josh.assignToManager(Leon)
        Zoe.assignToManager(Leon)
        Amelia.assignToManager(Leon)


        var production = Department()


        production.addManager(Masha)
        production.addManager(Leon)


        production.giveAllSalaries()

    }
