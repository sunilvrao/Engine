--file = LOAD 'src/test/resources/pig/CASLoginLog.txt' USING PigStorage(' ') AS (ticketDate: chararray,ticketTime: chararray,action: chararray,username: chararray,service: chararray,ticket: chararray);
file = LOAD '$inputfile' USING PigStorage(' ') AS (ticketDate: chararray,ticketTime: chararray,action: chararray,username: chararray,service: chararray,ticket: chararray);

trimmedfile = FOREACH file GENERATE TRIM(ticketDate) as ticketDate, TRIM(action) AS action, TRIM(username) AS username ,TRIM(ticket) AS ticket ;

selectedrows = FILTER trimmedfile BY action == 'SERVICE_TICKET_CREATED';

usersgroup = GROUP selectedrows BY username;

counts = FOREACH usersgroup GENERATE group AS username, COUNT(selectedrows) AS num_digits;


rmf $outputfile
--STORE counts INTO 'target/result' USING PigStorage('=');
STORE counts INTO '$outputfile' USING PigStorage('=');