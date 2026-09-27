# P6tem: Virtual Lab for Physics Experiments
P6tem (pi - six - tem) is a Java console-based application that simulates and
manages a set of physics experiments: mechanics (projectile motion) and circuits using OOP principles,
with CSV import/export and custom exception handling

# Classes
- `InvalidParameterException` `SimulationException` - custom exceptions
- `CircuitType` - enum
- `LabActivity` - abstract (base)
- `MechanicsExperiment` 
- `ProjectileMotionExperiment`
- `CircuitExperiment`
- `CSVExperimentLoader`
- `VirtualLab`
- `Main` sample `experiments.csv`

## Mermaid (UML)
```
classDiagram
    direction TB
    class VirtualLab {
        -String labName
        -LabActivity[] activities
        -int activityCount
        +addActivity(LabActivity activity) void
        +runAll() void
        +generateReport() String
        +exportReportToCSV(String filePath) void
    }
    class LabActivity {
        <<Abstract>>
        #String activityId
        #String activityName
        #String description
        #String startTime
        #String endTime
        #String status
        #int maxDurationMinutes
        +startActivity() void
        +endActivity() void
        +runSimulation() void*
        +calculateResults() String*
        +displayResults() void
    }
    class MechanicsExperiment {
        #double mass
        #double gravity
        +runSimulation()
        +calculateResults()
    }
    class ProjectileMotionExperiment {
        -double initialVelocity
        -double launchAngle
        -double initialHeight
        -double rangeResult
        -double maxHeightResult
        -double timeOfFlightResult
        +runSimulation()
        +calculateResults()
    }
    class CircuitExperiment {
        -double voltage
        -double[] resistances
        -double totalResistance
        -double current
        -CircuitType circuitType
        +runSimulation()
        +calculateResults()
    }
    class CircuitType {
        <<Enumeration>>
        SERIES
        PARALLEL
    }
    class CSVExperimentLoader {
        +loadFromCSV(String filePath) List~LabActivity~
    }
    class InvalidParameterException {
        -String parameterName
        +getMessage()
        +getParameterName() String
    }
    class SimulationException {
        -int errorCode
        +getMessage()
        +getErrorCode()
    }
    VirtualLab "1" o-- "0..*" LabActivity : contains
    LabActivity <|-- MechanicsExperiment
    LabActivity <|-- CircuitExperiment
    MechanicsExperiment <|-- ProjectileMotionExperiment
    CircuitExperiment ..> CircuitType : uses
    CSVExperimentLoader ..> LabActivity : creates
    CSVExperimentLoader ..> CircuitExperiment : instantiates
    CSVExperimentLoader ..> ProjectileMotionExperiment : instantiates
    VirtualLab ..> MechanicsExperiment : instantiates
    VirtualLab ..> InvalidParameterException : throws
    VirtualLab ..> SimulationException : throws
```

## Status
- [x] InvalidParameterException
- [x] SimulationException
- [x] CircuitType
- [ ] LabActivity
- [ ] MechanicsExperiment
- [ ] ProjectileMotionExperiment
- [ ] CircuitExperiment
- [ ] CSVExperimentLoader
- [ ] VirtualLab

## Team
Built by:
Tan, Sean Handrea
Tiria, Kenjhi
Villanueva, Arwin Luigi
Zablan, Alsher Vinz
for Final Project in 6OOP: Ma'am Carisma Caro | 1st Semester
