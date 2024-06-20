package com.fork.forkfork.info.domain.entity

class Job(
    val jobCategory: JobCategory,
    val jobName: String,
)

enum class JobCategory {
    STUDENT,
    EMPLOYEE,
    FREELANCER,
    ETC,
}
