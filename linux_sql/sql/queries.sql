SELECT cpu_number, id as host_id, total_mem
    FROM PUBLIC.host_info
    GROUP BY cpu_number
ORDER BY total_mem DESC;

-- function to round off every 5 minutes
CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
$$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
    LANGUAGE PLPGSQL;

SELECT host_id, hostname, round5(host_usage.timestamp) AS timestamp_5,
       AVG((total_mem - memory_free)*100/total_mem) AS avg_used_mem_percent
       FROM host_usage
         INNER JOIN host_info
            ON host_info.id = host_usage.host_id
    GROUP BY host_id, timestamp_5, hostname
    ORDER BY host_id;

SELECT host_id, timestamp, round5(timestamp), COUNT(*) as num_of_datapoints
    FROM host_usage
    GROUP BY host_id, timestamp, round5(timestamp)
    HAVING COUNT(*) < 3
ORDER BY host_id;