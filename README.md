# Multithreading-Project1-NoSemaphores

This project was done for my CS 340 Operating Systems Principles. Professor gave us an arbitrary scenario in which each student individually was required to implement using Multithreading in Java WITHOUT the usage of Semaphores.

## Instructions:

The project must be done individually - no exceptions. Plagiarism is not accepted. Receiving
and/or giving answers and code from/to other students enrolled in this or a previous semester,
or a third party source including the Internet is academic dishonesty subject to standard
University policies.
The objective of this project is to familiarize you with the creation and execution of threads using
the Thread class methods. You should use, when necessary, the following methods to
synchronize all threads: run(), start(), currentThread(), getName(), join(), yield(),
sleep(time), isAlive(), getPriority(), setPriority(), interrupt(), isInterrupted().
The use of semaphores to synchronize threads is strictly DISALLOWED. Additionally, you are
NOT PERMITTED to use any of the following: wait(), notify(), notifyAll(), the synchronized
keyword (for methods or blocks), and any synchronized collections or synchronization tools that
were not discussed in class.
You CAN, however, use the modifier volatile and the AtomicInteger and AtomicBoolean
classes if you choose to.

Directions: Synchronize the voter, ID_checkers, kiosk_helper, scanning_helper threads in the
context of the problem described below. The number of voters can be entered as a command line
argument. Please refer to and read carefully the project notes, tips and guidelines before
starting the project.

## Scenario: Today is Election Day 

Voters are anxious to vote. Once arrived at the designated voting place ((simulated by sleep of
random time) they will have to busy wait on line for their ID to be checked (you might use a
boolean array/vector to keep track of their order). They will busy wait until one of the available
ID_checkers lets them move on.
Once done with the ID check, voters can enter the voting kiosk line. The next step is to fill out a
ballot form with their information. There are num_k kiosks. The voter will pick the kiosk with the
shortest line and wait until they get in front so they can enter their information (simulate the wait
using sleep for a long time). Due to COVID a lot of regulations are in place. A kiosk_helper
will control all this movement. He will inform the next voter when a kiosk becomes available and
that he/she can move on (have the kiosk_helper interrupt the voter). When a voter is done
completing his/her ballot (and usually it takes some time – simulate by sleep of random time),
he/she will let the helper know and the helper will interrupt the next voter on that specific line so
that he/she can move on (Use interrupt() and isInterrupted( ); for the voter have a println
inside the catch block so you can check the interrupt).

Finally, the last step. Now, the voters will rush to the room with scanning machines. (simulate
this by increasing their priority. Use getPriority(), setPriority(), sleep(random time). After the
voter has increased its priority, (s)he will sleep a random time and as soon as (s)he wakes up
make sure you reset his/her priority back to the normal value. Next voters will busy_wait for
the scanning helper to allow them to use the scanning machines. There are num_sm scanning
machines. Once all scanners are available, the scanning_helper will let num_sm to move on.
However, the voters get nervous and slowdown a bit. Each of them will yield (implement this by
using yield() two times). Finally, they will scan their ballot (simulate by sleep of random time)
and leave the scanning room. They are ready to leave the building with a very patriotic feeling
that they exercised they right to vote and maybe tomorrow will be a better day. Leaving will be
done in a specific order in groups. Voters that were at the same time in the scanning_room will
join the voter with the highest id (((use join() and isAlive()).
For example if at a given time in the scanning room they were v0, v2, v12, v17, then v0,v2,v12
will join v17. V17 will leave first.
After all the voters leave the helpers will be able to leave as well.

<b>Default values:</b>
- num_voters = 20
- num_ID_checkers = 3
- num_k = 3
- num_sm = 4
