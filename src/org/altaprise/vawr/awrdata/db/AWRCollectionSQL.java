package org.altaprise.vawr.awrdata.db;

public class AWRCollectionSQL {


    public static String getDbIdSQL() {
        String dbIdSQL =
            " SELECT  distinct dbid, db_name FROM dba_hist_database_instance ";
        return dbIdSQL;
    }

    public static String getMainAWRMetricsSQL(long dbID, long snapIdMin,
                                              long snapIdMax) {
        String sql =
            "select snap_id \"snap\",num_interval \"dur_m\", end_time \"end\",inst \"inst\", " +
            " max(decode(metric_name,'Host CPU Utilization (%)',                average,null)) \"os_cpu\", " +
            " max(decode(metric_name,'Host CPU Utilization (%)',                maxval,null)) \"os_cpu_max\", " +
            " max(decode(metric_name,'Host CPU Utilization (%)',                STANDARD_DEVIATION,null)) \"os_cpu_sd\", " +
            " max(decode(metric_name,'Database Wait Time Ratio',                round(average,1),null)) \"db_wait_ratio\", " +
            " max(decode(metric_name,'Database CPU Time Ratio',                 round(average,1),null)) \"db_cpu_ratio\", " +
            " max(decode(metric_name,'CPU Usage Per Sec',                       round(average/100,3),null)) \"cpu_per_s\", " +
            " max(decode(metric_name,'CPU Usage Per Sec',                       round(STANDARD_DEVIATION/100,3),null)) \"cpu_per_s_sd\", " +
            " max(decode(metric_name,'Host CPU Usage Per Sec',                  round(average/100,3),null)) \"h_cpu_per_s\", " +
            " max(decode(metric_name,'Host CPU Usage Per Sec',                  round(STANDARD_DEVIATION/100,3),null)) \"h_cpu_per_s_sd\", " +
            " max(decode(metric_name,'Average Active Sessions',                 average,null)) \"aas\", " +
            " max(decode(metric_name,'Average Active Sessions',                 STANDARD_DEVIATION,null)) \"aas_sd\", " +
            " max(decode(metric_name,'Average Active Sessions',                 maxval,null)) \"aas_max\", " +
            " max(decode(metric_name,'Database Time Per Sec',                   average,null)) \"db_time\", " +
            " max(decode(metric_name,'Database Time Per Sec',                   STANDARD_DEVIATION,null)) \"db_time_sd\", " +
            " max(decode(metric_name,'SQL Service Response Time',               average,null)) \"sql_res_t_cs\", " +
            " max(decode(metric_name,'Background Time Per Sec',                 average,null)) \"bkgd_t_per_s\", " +
            " max(decode(metric_name,'Logons Per Sec',                          average,null)) \"logons_s\", " +
            " max(decode(metric_name,'Current Logons Count',                    average,null)) \"logons_total\", " +
            " max(decode(metric_name,'Executions Per Sec',                      average,null)) \"exec_s\", " +
            " max(decode(metric_name,'Hard Parse Count Per Sec',                average,null)) \"hard_p_s\", " +
            " max(decode(metric_name,'Logical Reads Per Sec',                   average,null)) \"l_reads_s\", " +
            " max(decode(metric_name,'User Commits Per Sec',                    average,null)) \"commits_s\", " +
            " max(decode(metric_name,'Physical Read Total Bytes Per Sec',       round((average)/1024/1024,1),null)) \"read_mb_s\", " +
            " max(decode(metric_name,'Physical Read Total Bytes Per Sec',       round((maxval)/1024/1024,1),null)) \"read_mb_s_max\", " +
            " max(decode(metric_name,'Physical Read Total IO Requests Per Sec', average,null)) \"read_iops\", " +
            " max(decode(metric_name,'Physical Read Total IO Requests Per Sec', maxval,null)) \"read_iops_max\", " +
            " max(decode(metric_name,'Physical Reads Per Sec',                  average,null)) \"read_bks\", " +
            " max(decode(metric_name,'Physical Reads Direct Per Sec',           average,null)) \"read_bks_direct\", " +
            " max(decode(metric_name,'Physical Write Total Bytes Per Sec',      round((average)/1024/1024,1),null)) \"write_mb_s\", " +
            " max(decode(metric_name,'Physical Write Total Bytes Per Sec',      round((maxval)/1024/1024,1),null)) \"write_mb_s_max\", " +
            " max(decode(metric_name,'Physical Write Total IO Requests Per Sec',average,null)) \"write_iops\", " +
            " max(decode(metric_name,'Physical Write Total IO Requests Per Sec',maxval,null)) \"write_iops_max\", " +
            " max(decode(metric_name,'Physical Writes Per Sec',                 average,null)) \"write_bks\", " +
            " max(decode(metric_name,'Physical Writes Direct Per Sec',          average,null)) \"write_bks_direct\", " +
            " max(decode(metric_name,'Redo Generated Per Sec',                  round((average)/1024/1024,1),null)) \"redo_mb_s\", " +
            " max(decode(metric_name,'DB Block Gets Per Sec',                   average,null)) \"db_block_gets_s\", " +
            " max(decode(metric_name,'DB Block Changes Per Sec',                average,null)) \"db_block_changes_s\", " +
            " max(decode(metric_name,'GC CR Block Received Per Second',         average,null)) \"gc_cr_rec_s\", " +
            " max(decode(metric_name,'GC Current Block Received Per Second',    average,null)) \"gc_cu_rec_s\", " +
            " max(decode(metric_name,'Global Cache Average CR Get Time',        average,null)) \"gc_cr_get_cs\", " +
            " max(decode(metric_name,'Global Cache Average Current Get Time',   average,null)) \"gc_cu_get_cs\", " +
            " max(decode(metric_name,'Global Cache Blocks Corrupted',           average,null)) \"gc_bk_corrupted\", " +
            " max(decode(metric_name,'Global Cache Blocks Lost',                average,null)) \"gc_bk_lost\", " +
            " max(decode(metric_name,'Active Parallel Sessions',                average,null)) \"px_sess\", " +
            " max(decode(metric_name,'Active Serial Sessions',                  average,null)) \"se_sess\", " +
            " max(decode(metric_name,'Average Synchronous Single-Block Read Latency', average,null)) \"s_blk_r_lat\", " +
            " max(decode(metric_name,'Cell Physical IO Interconnect Bytes',     round((average)/1024/1024,1),null)) \"cell_io_int_mb\", " +
            " max(decode(metric_name,'Cell Physical IO Interconnect Bytes',     round((maxval)/1024/1024,1),null)) \"cell_io_int_mb_max\" " +
            "   from( " +
            "   select  snap_id,num_interval,to_char(end_time,'YY/MM/DD HH24:MI') end_time,instance_number inst,metric_name,round(average,1) average, " +
            "   round(maxval,1) maxval,round(standard_deviation,1) standard_deviation " +
            "  from dba_hist_sysmetric_summary " + " where dbid = " + dbID +
            "  and snap_id between " + snapIdMin + "  and " + snapIdMax +
            "   and metric_name in ('Host CPU Utilization (%)','CPU Usage Per Sec','Host CPU Usage Per Sec','Average Active Sessions','Database Time Per Sec', " +
            "  'Executions Per Sec','Hard Parse Count Per Sec','Logical Reads Per Sec','Logons Per Sec', " +
            "  'Physical Read Total Bytes Per Sec','Physical Read Total IO Requests Per Sec','Physical Reads Per Sec','Physical Write Total Bytes Per Sec', " +
            "  'Redo Generated Per Sec','User Commits Per Sec','Current Logons Count','DB Block Gets Per Sec','DB Block Changes Per Sec', " +
            "  'Database Wait Time Ratio','Database CPU Time Ratio','SQL Service Response Time','Background Time Per Sec', " +
            "  'Physical Write Total IO Requests Per Sec','Physical Writes Per Sec','Physical Writes Direct Per Sec','Physical Writes Direct Lobs Per Sec', " +
            "  'Physical Reads Direct Per Sec','Physical Reads Direct Lobs Per Sec', " +
            "  'GC CR Block Received Per Second','GC Current Block Received Per Second','Global Cache Average CR Get Time','Global Cache Average Current Get Time', " +
            "  'Global Cache Blocks Corrupted','Global Cache Blocks Lost', " +
            "  'Active Parallel Sessions','Active Serial Sessions','Average Synchronous Single-Block Read Latency','Cell Physical IO Interconnect Bytes' " +
            "     ) " + "  ) " +
            "  group by snap_id,num_interval, end_time,inst " +
            "  order by snap_id, end_time,inst ";

        return sql;
    }
    
    public static String getMemoryUtilizationSQL(long dbId, long startSnapId, long endSnapId) {
        String memSQL =     " SELECT snap_id, " +
        " instance_number, " +
        " MAX (DECODE (stat_name, \'SGA\', stat_value, NULL)) \"SGA\", " +
        " MAX (DECODE (stat_name, \'PGA\', stat_value, NULL)) \"PGA\", " +
        " MAX (DECODE (stat_name, 'SGA', stat_value, NULL)) + MAX (DECODE (stat_name, 'PGA', stat_value, " +
        " NULL)) \"SGA_PGA_TOT\" " +
        " FROM " +
        " (SELECT snap_id, " +
        "    instance_number, " +
        "    ROUND (SUM (bytes) / 1024 / 1024 / 1024, 1) stat_value, " +
        "    MAX (\'SGA\') stat_name " +
        "   FROM dba_hist_sgastat " +
        "  WHERE dbid = " + dbId +
        "    AND snap_id BETWEEN " + startSnapId + " AND " + endSnapId  +
        " GROUP BY snap_id, " +
        "     instance_number " +
        " UNION ALL " +
        "   SELECT snap_id, " +
        "      instance_number, " +
        "      ROUND (value / 1024 / 1024 / 1024, 1) stat_value, " +
        "      \'PGA\' stat_name " +
        "     FROM dba_hist_pgastat " +
        "    WHERE dbid = " + dbId +
        "      AND snap_id BETWEEN " + startSnapId + " AND " + endSnapId +
        "      AND NAME = 'total PGA allocated' " +
        "  ) " +
        " GROUP BY snap_id, " +
        "    instance_number " +
        " ORDER BY snap_id, " +
        "    instance_number ";

        return memSQL;
    }

    public static String getMinSnapIdSQL(long dbId, long numDays) {
        String minSnapIdSQL =
            " SELECT min(snap_id) - 1 snap_min1 " + " FROM dba_hist_snapshot " +
            " WHERE dbid = " + dbId + " and begin_interval_time > ( " +
            " SELECT max(begin_interval_time) - " + numDays +
            " FROM dba_hist_snapshot  " + " where dbid = " + dbId + " )";

        return minSnapIdSQL;
    }


    public static String getMaxSnapIdSQL(long dbId) {
        String maxSnapIdSQL =
            " SELECT max(snap_id) snap_max1 " + " FROM dba_hist_snapshot " +
            " WHERE dbid = " + dbId;

        return maxSnapIdSQL;
    }

    public static String getAllSnapIdsAndTimesSQL(long dbId) {
        String allSnaps =
            "SELECT snap_id, startup_time, begin_interval_time, end_interval_time " +
            " FROM dba_hist_snapshot " + " WHERE dbid = " + dbId + " order by snap_id";

        return allSnaps;
    }
    
    public static String getAvgActiveSessionsSQL(long dbId, long startSnapId, long endSnapId) {
        
        String sql = 
        " SELECT snap_id, \n" +
        "    wait_class, \n" +
        "    ROUND (SUM (pSec), 2) avg_sess \n" +
        "   FROM \n" +
        "    (SELECT snap_id, \n" +
        "        wait_class, \n" +
        "        p_tmfg / 1000000 / ela pSec \n" +
        "       FROM \n" +
        "        (SELECT (CAST (s.end_interval_time AS DATE) - CAST (s.begin_interval_time AS DATE)) * 24 * \n" +
        "            3600 ela, \n" +
        "            s.snap_id, \n" +
        "            wait_class, \n" +
        "            e.event_name, \n" +
        "            CASE WHEN s.begin_interval_time = s.startup_time \n" +
        "                       -- compare to e.time_waited_micro_fg for 10.2? \n" +
        "                THEN e.TIME_WAITED_MICRO_FG \n" +
        "                ELSE e.TIME_WAITED_MICRO_FG - lag (e.TIME_WAITED_MICRO_FG) over (partition BY \n" +
        "                    event_id, e.dbid, e.instance_number, s.startup_time order by e.snap_id) \n" +
        "            END p_tmfg \n" +
        "           FROM dba_hist_snapshot s, \n" +
        "            dba_hist_system_event e \n" +
        "          WHERE s.dbid = e.dbid \n" +
        "            AND s.dbid = to_number(" + dbId + ") \n" +
        "            AND e.dbid = to_number(" + dbId + ") \n" +
        "            AND s.instance_number = e.instance_number \n" +
        "            AND s.snap_id = e.snap_id \n" +
        "            AND s.snap_id BETWEEN to_number("+startSnapId+") and to_number("+endSnapId+") \n" +
        "            AND e.snap_id BETWEEN to_number("+startSnapId+") and to_number("+endSnapId+") \n" +
        "            AND e.wait_class != 'Idle' \n" +
        "      UNION ALL \n" +
        "         SELECT (CAST (s.end_interval_time AS DATE) - CAST (s.begin_interval_time AS DATE)) * 24 * \n" +
        "            3600 ela, \n" +
        "            s.snap_id, \n" +
        "            t.stat_name wait_class, \n" +
        "            t.stat_name event_name, \n" +
        "            CASE WHEN s.begin_interval_time = s.startup_time \n" +
        "                THEN t.value \n" +
        "                ELSE t.value - lag (value) over (partition BY stat_id, t.dbid, t.instance_number, \n" +
        "                    s.startup_time order by t.snap_id) \n" +
        "            END p_tmfg \n" +
        "           FROM dba_hist_snapshot s, \n" +
        "            dba_hist_sys_time_model t \n" +
        "          WHERE s.dbid = t.dbid \n" +
        "            AND s.dbid = to_number(" + dbId + ") \n" +
        "            AND s.instance_number = t.instance_number \n" +
        "            AND s.snap_id = t.snap_id \n" +
        "            AND s.snap_id BETWEEN to_number("+startSnapId+") and to_number("+endSnapId+") \n" +
        "                       AND t.snap_id BETWEEN to_number("+startSnapId+") and to_number("+endSnapId+") \n" +
        "            AND t.stat_name = 'DB CPU' \n" +
        "        ) \n" +
        "               where p_tmfg is not null \n" +
        "    ) \n" +
        "GROUP BY snap_id, \n" +
        "    wait_class \n" +
        "ORDER BY snap_id, \n" +
        "    wait_class  \n";
        return sql;
    }
    
    public static String getStorageMetricsSQL() {
        String sqlString = 
        " DECLARE \n" +
           " v_num_disks    NUMBER; \n" +
           " v_group_number   NUMBER; \n" +
           " v_max_total_mb   NUMBER; \n" +
        "  \n" +
           " v_required_free_mb   NUMBER; \n" +
           " v_usable_mb      NUMBER; \n" +
           " v_cell_usable_mb   NUMBER; \n" +
           " v_one_cell_usable_mb   NUMBER; \n" +
           " v_enuf_free      BOOLEAN := FALSE; \n" +
           " v_enuf_free_cell   BOOLEAN := FALSE; \n" +
        "  \n" +
           " v_req_mirror_free_adj_factor   NUMBER := 1.10; \n" +
           " v_req_mirror_free_adj         NUMBER := 0; \n" +
           " v_one_cell_req_mir_free_mb     NUMBER  := 0; \n" +
        "  \n" +
           " v_disk_desc      VARCHAR(10) := 'SINGLE'; \n" +
           " v_offset      NUMBER := 50; \n" +
        "  \n" +
           " v_db_version   VARCHAR2(8); \n" +
           " v_inst_name    VARCHAR2(1); \n" +
        "  \n" +
           " v_cfc_fail_msg VARCHAR2(152); \n" +
        "     \n" +
           " dg_name VARCHAR(200) := ''; \n" +
            " dg_num_disks VARCHAR(200) := ''; \n" +
              " dg_max_tot_mb VARCHAR(200) := ''; \n" +
              " dg_tot_mb VARCHAR(200) := ''; \n" +
              " dg_tot_mb_used VARCHAR(200) := ''; \n" +
              " dg_tot_mb_free VARCHAR(200) := ''; \n" +
        " dg_tot_pct_free VARCHAR(200) := ''; \n" +
        "  \n" +
        " BEGIN \n" +
        "  \n" +
           " SELECT substr(version,1,8), substr(instance_name,1,1)    INTO v_db_version, v_inst_name    FROM v$instance; \n" +
        "  \n" +
           " IF v_inst_name <> '+' THEN \n" +
              " DBMS_OUTPUT.PUT_LINE('ERROR: THIS IS NOT AN ASM INSTANCE!  PLEASE LOG ON TO AN ASM INSTANCE AND RE-RUN THIS SCRIPT.'); \n" +
              " GOTO the_end; \n" +
           " END IF; \n" +
        "  \n" +
            " DBMS_OUTPUT.PUT_LINE('------ DISK and CELL Failure Diskgroup Space Reserve Requirements  ------'); \n" +
            " DBMS_OUTPUT.PUT_LINE(' This procedure determines how much space you need to survive a DISK or CELL failure. It also shows the usable space '); \n" +
            " DBMS_OUTPUT.PUT_LINE(' available when reserving space for disk or cell failure.  '); \n" +
           " DBMS_OUTPUT.PUT_LINE(' Please see MOS note 1551288.1 for more information.  '); \n" +
           " DBMS_OUTPUT.PUT_LINE('.  .  .'); \n" +
            " DBMS_OUTPUT.PUT_LINE(' Description of Derived Values:'); \n" +
            " DBMS_OUTPUT.PUT_LINE(' One Cell Required Mirror Free MB : Required Mirror Free MB to permit successful rebalance after losing largest CELL regardless of redundancy type'); \n" +
            " DBMS_OUTPUT.PUT_LINE(' Disk Required Mirror Free MB     : Space needed to rebalance after loss of single or double disk failure (for normal or high redundancy)'); \n" +
            " DBMS_OUTPUT.PUT_LINE(' Disk Usable File MB              : Usable space available after reserving space for disk failure and accounting for mirroring'); \n" +
            " DBMS_OUTPUT.PUT_LINE(' Cell Usable File MB              : Usable space available after reserving space for SINGLE cell failure and accounting for mirroring'); \n" +
           " DBMS_OUTPUT.PUT_LINE('.  .  .'); \n" +
        "  \n" +
           " IF (v_db_version = '11.2.0.3') OR (v_db_version = '11.2.0.4') OR (v_db_version = '12.1.0.1')  THEN \n" +
              " v_req_mirror_free_adj_factor := 1.10; \n" +
              " DBMS_OUTPUT.PUT_LINE('ASM Version: '||v_db_version); \n" +
           " ELSE \n" +
              " v_req_mirror_free_adj_factor := 1.5; \n" +
              " DBMS_OUTPUT.PUT_LINE('ASM Version: '||v_db_version||'  - WARNING DISK FAILURE COVERAGE ESTIMATES HAVE NOT BEEN VERIFIED ON THIS VERSION!'); \n" +
           " END IF; \n" +
        "  \n" +
           " DBMS_OUTPUT.PUT_LINE('.  .  .'); \n" +
        " -- Set up headings \n" +
             " DBMS_OUTPUT.PUT_LINE('----------------------------------------------------------------------------------------------------------------------------------------------------'); \n" +
              " DBMS_OUTPUT.PUT('|          '); \n" +
              " DBMS_OUTPUT.PUT('|         '); \n" +
              " DBMS_OUTPUT.PUT('|     '); \n" +
              " DBMS_OUTPUT.PUT('|          '); \n" +
              " DBMS_OUTPUT.PUT('|            '); \n" +
              " DBMS_OUTPUT.PUT('|            '); \n" +
              " DBMS_OUTPUT.PUT('|            '); \n" +
              " DBMS_OUTPUT.PUT('|Cell Req''d  '); \n" +
              " DBMS_OUTPUT.PUT('|Disk Req''d  '); \n" +
              " DBMS_OUTPUT.PUT('|            '); \n" +
              " DBMS_OUTPUT.PUT('|            '); \n" +
              " DBMS_OUTPUT.PUT('|    '); \n" +
              " DBMS_OUTPUT.PUT('|    '); \n" +
              " DBMS_OUTPUT.PUT('|       '); \n" +
              " DBMS_OUTPUT.PUT_Line('|'); \n" +
              " DBMS_OUTPUT.PUT('|          '); \n" +
              " DBMS_OUTPUT.PUT('|DG       '); \n" +
              " DBMS_OUTPUT.PUT('|Num  '); \n" +
              " DBMS_OUTPUT.PUT('|Disk Size '); \n" +
              " DBMS_OUTPUT.PUT('|DG Total    '); \n" +
              " DBMS_OUTPUT.PUT('|DG Used     '); \n" +
              " DBMS_OUTPUT.PUT('|DG Free     '); \n" +
              " DBMS_OUTPUT.PUT('|Mirror Free '); \n" +
              " DBMS_OUTPUT.PUT('|Mirror Free '); \n" +
              " DBMS_OUTPUT.PUT('|Disk Usable '); \n" +
              " DBMS_OUTPUT.PUT('|Cell Usable '); \n" +
              " DBMS_OUTPUT.PUT('|    '); \n" +
              " DBMS_OUTPUT.PUT('|    '); \n" +
              " DBMS_OUTPUT.PUT_LINE('|PCT    |'); \n" +
              " DBMS_OUTPUT.PUT('|DG Name   '); \n" +
              " DBMS_OUTPUT.PUT('|Type     '); \n" +
              " DBMS_OUTPUT.PUT('|Disks'); \n" +
              " DBMS_OUTPUT.PUT('|MB        '); \n" +
              " DBMS_OUTPUT.PUT('|MB          '); \n" +
              " DBMS_OUTPUT.PUT('|MB          '); \n" +
              " DBMS_OUTPUT.PUT('|MB          '); \n" +
              " DBMS_OUTPUT.PUT('|MB          '); \n" +
              " DBMS_OUTPUT.PUT('|MB          '); \n" +
              " DBMS_OUTPUT.PUT('|File MB     '); \n" +
              " DBMS_OUTPUT.PUT('|File MB     '); \n" +
              " DBMS_OUTPUT.PUT('|DFC '); \n" +
              " DBMS_OUTPUT.PUT('|CFC '); \n" +
              " DBMS_OUTPUT.PUT_LINE('|Util   |'); \n" +
             " DBMS_OUTPUT.PUT_LINE('----------------------------------------------------------------------------------------------------------------------------------------------------'); \n" +
        "  \n" +
           " FOR dg IN (SELECT name, type, group_number, total_mb, free_mb, required_mirror_free_mb FROM v$asm_diskgroup ORDER BY name) LOOP \n" +
        "  \n" +
              " v_enuf_free := FALSE; \n" +
        "  \n" +
             " v_req_mirror_free_adj := dg.required_mirror_free_mb * v_req_mirror_free_adj_factor; \n" +
        "  \n" +
              " -- Find largest amount of space allocated to a cell \n" +
              " SELECT sum(disk_cnt), max(max_total_mb), max(sum_total_mb)*v_req_mirror_free_adj_factor \n" +
             " INTO v_num_disks, v_max_total_mb, v_one_cell_req_mir_free_mb \n" +
              " FROM (SELECT count(1) disk_cnt, max(total_mb) max_total_mb, sum(total_mb) sum_total_mb \n" +
              " FROM v$asm_disk \n" +
             " WHERE group_number = dg.group_number \n" +
             " GROUP BY failgroup); \n" +
        "  \n" +
              " -- Eighth Rack \n" +
              " IF dg.type = 'NORMAL' THEN \n" +
        "  \n" +
                 " -- Eighth Rack \n" +
                 " IF (v_num_disks < 36) THEN \n" +
                    " -- Use eqn: y = 1.21344 x+ 17429.8 \n" +
                    " v_required_free_mb :=  1.21344 * v_max_total_mb + 17429.8; \n" +
                    " IF dg.free_mb > v_required_free_mb THEN v_enuf_free := TRUE; END IF; \n" +
                 " -- Quarter Rack \n" +
                 " ELSIF (v_num_disks >= 36 AND v_num_disks < 84) THEN \n" +
                    " -- Use eqn: y = 1.07687 x+ 19699.3 \n" +
                                " -- Revised 2/21/14 for 11.2.0.4 to use eqn: y=0.803199x + 156867, more space but safer \n" +
                    " v_required_free_mb := 0.803199 * v_max_total_mb + 156867; \n" +
                    " IF dg.free_mb > v_required_free_mb THEN v_enuf_free := TRUE; END IF; \n" +
                 " -- Half Rack \n" +
                 " ELSIF (v_num_disks >= 84 AND v_num_disks < 168) THEN \n" +
                    " -- Use eqn: y = 1.02475 x+53731.3 \n" +
                    " v_required_free_mb := 1.02475 * v_max_total_mb + 53731.3; \n" +
                    " IF dg.free_mb > v_required_free_mb THEN v_enuf_free := TRUE; END IF; \n" +
                 " -- Full rack is most conservative, it will be default \n" +
                 " ELSE \n" +
                    " -- Use eqn: y = 1.33333 x+83220. \n" +
                    " v_required_free_mb := 1.33333 * v_max_total_mb + 83220; \n" +
                    " IF dg.free_mb > v_required_free_mb THEN v_enuf_free := TRUE; END IF; \n" +
        "  \n" +
                 " END IF; \n" +
        "  \n" +
                 " -- DISK usable file MB \n" +
                 " v_usable_mb := ROUND((dg.free_mb - v_required_free_mb)/2); \n" +
                 " v_disk_desc := 'ONE disk'; \n" +
        "  \n" +
                 " -- CELL usable file MB \n" +
                 " v_cell_usable_mb := ROUND( (dg.free_mb - v_one_cell_req_mir_free_mb)/2 ); \n" +
                 " v_one_cell_usable_mb := v_cell_usable_mb; \n" +
        "  \n" +
              " ELSE \n" +
                 " -- HIGH redundancy \n" +
        "  \n" +
                 " -- Eighth Rack \n" +
                 " IF (v_num_disks <= 18) THEN \n" +
                    " -- Use eqn: y = 4x + 0 \n" +
                                " -- Updated for 11.2.0.4 to higher value:  y = 3.84213x + 84466.4 \n" +
                    " v_required_free_mb :=  3.84213 * v_max_total_mb + 84466.4; \n" +
                    " IF dg.free_mb > v_required_free_mb THEN v_enuf_free := TRUE; END IF; \n" +
                 " -- Quarter Rack \n" +
                 " ELSIF (v_num_disks > 18 AND v_num_disks <= 36) THEN \n" +
                    " -- Use eqn: y = 3.87356 x+417692. \n" +
                    " v_required_free_mb := 3.87356 * v_max_total_mb + 417692; \n" +
                    " IF dg.free_mb > v_required_free_mb THEN v_enuf_free := TRUE; END IF; \n" +
                 " -- Half Rack \n" +
                 " ELSIF (v_num_disks > 36 AND v_num_disks <= 84) THEN \n" +
                    " -- Use eqn: y = 2.02222 x+56441.6 \n" +
                    " v_required_free_mb := 2.02222 * v_max_total_mb + 56441.6; \n" +
                    " IF dg.free_mb > v_required_free_mb THEN v_enuf_free := TRUE; END IF; \n" +
                 " -- Full rack is most conservative, it will be default \n" +
                 " ELSE \n" +
                    " -- Use eqn: y = 2.14077 x+54276.4 \n" +
                    " v_required_free_mb := 2.14077 * v_max_total_mb + 54276.4; \n" +
                    " IF dg.free_mb > v_required_free_mb THEN v_enuf_free := TRUE; END IF; \n" +
        "  \n" +
                 " END IF; \n" +
        "  \n" +
                 " -- DISK usable file MB \n" +
                 " v_usable_mb := ROUND((dg.free_mb - v_required_free_mb)/3); \n" +
                 " v_disk_desc := 'TWO disks'; \n" +
        "  \n" +
                 " -- CELL usable file MB \n" +
                 " v_one_cell_usable_mb := ROUND( (dg.free_mb - v_one_cell_req_mir_free_mb)/3 ); \n" +
        "  \n" +
              " END IF; \n" +
              " DBMS_OUTPUT.PUT('|'||RPAD(dg.name,v_offset-40)); \n" +
              " DBMS_OUTPUT.PUT('|'||RPAD(nvl(dg.type,'  '),v_offset-41)); \n" +
              " DBMS_OUTPUT.PUT('|'||LPAD(TO_CHAR(v_num_disks),v_offset-45)); \n" +
              " DBMS_OUTPUT.PUT('|'||TO_CHAR(v_max_total_mb,'9,999,999')); \n" +
              " DBMS_OUTPUT.PUT('|'||TO_CHAR(dg.total_mb,'999,999,999')); \n" +
              " DBMS_OUTPUT.PUT('|'||TO_CHAR(dg.total_mb - dg.free_mb,'999,999,999')); \n" +
              " DBMS_OUTPUT.PUT('|'||TO_CHAR(dg.free_mb,'999,999,999')); \n" +
              " DBMS_OUTPUT.PUT('|'||TO_CHAR(ROUND(v_one_cell_req_mir_free_mb),'999,999,999')); \n" +
              " DBMS_OUTPUT.PUT('|'||TO_CHAR(ROUND(v_required_free_mb),'999,999,999')); \n" +
              " DBMS_OUTPUT.PUT('|'||TO_CHAR(ROUND(v_usable_mb),'999,999,999')); \n" +
              " DBMS_OUTPUT.PUT('|'||TO_CHAR(ROUND(v_one_cell_usable_mb),'999,999,999'));  \n" +
        "  \n" +
              " dg_name  := dg_name || '~' || dg.name; \n" +
              " dg_num_disks := dg_num_disks  || '~' || TO_CHAR(v_num_disks); \n" +
              " dg_max_tot_mb  := dg_max_tot_mb  || '~' || TO_CHAR(v_max_total_mb); \n" +
              " dg_tot_mb  := dg_tot_mb  || '~' || TO_CHAR(dg.total_mb,'9,999,999'); \n" +
              " dg_tot_mb_used  := dg_tot_mb_used  || '~' || TO_CHAR(dg.total_mb - dg.free_mb,'999,999,999'); \n" +
              " dg_tot_mb_free  := dg_tot_mb_free  || '~' || TO_CHAR(dg.free_mb,'999,999,999'); \n" +
        " dg_tot_pct_free  := dg_tot_pct_free  || '~' || TO_CHAR((((dg.total_mb - dg.free_mb)/dg.total_mb)*100),'999.9')||CHR(37); \n" + 
        "  \n" +
              " IF v_enuf_free THEN \n" +
                 " DBMS_OUTPUT.PUT('|'||'PASS'); \n" +
              " ELSE \n" +
                 " DBMS_OUTPUT.PUT('|'||'FAIL'); \n" +
              " END IF; \n" +
        "  \n" +
             " IF dg.type = 'NORMAL' THEN \n" +
                " -- Calc Free Space for Rebalance Due to Cell Failure \n" +
                " IF v_req_mirror_free_adj < dg.free_mb THEN \n" +
                   " DBMS_OUTPUT.PUT('|'||'PASS'); \n" +
                " ELSE \n" +
                    " DBMS_OUTPUT.PUT('|'||'FAIL'); \n" +
                    " v_cfc_fail_msg := 'Enough Free Space to Rebalance after loss of ONE cell: WARNING (However, cell failure is very rare)'; \n" +
                " END IF; \n" +
             " ELSE \n" +
                " -- Calc Free Space for Rebalance Due to Single Cell Failure \n" +
                " IF v_one_cell_req_mir_free_mb < dg.free_mb THEN \n" +
                   " DBMS_OUTPUT.PUT('|'||'PASS'); \n" +
                " ELSE \n" +
                   " DBMS_OUTPUT.PUT('|'||'FAIL'); \n" +
                   " v_cfc_fail_msg := 'Enough Free Space to Rebalance after loss of ONE cell: WARNING (However, cell failure is very rare and high redundancy offers ample protection already)'; \n" +
                " END IF; \n" +
        "  \n" +
             " END IF; \n" +
        "  \n" +
             " -- Calc Disk Utilization Percentage \n" +
                " IF dg.total_mb > 0 THEN \n" +
                   " DBMS_OUTPUT.PUT_LINE('|'||TO_CHAR((((dg.total_mb - dg.free_mb)/dg.total_mb)*100),'999.9')||CHR(37)||'|'); \n" +
                " ELSE \n" +
                   " DBMS_OUTPUT.PUT_LINE('|       |'); \n" +
                " END IF; \n" +
        "  \n" +
           " END LOOP; \n" +
        "  \n" +
             " DBMS_OUTPUT.PUT_LINE('----------------------------------------------------------------------------------------------------------------------------------------------------'); \n" +
           " <<the_end>> \n" +
        "  \n" +
           " IF v_cfc_fail_msg is not null THEN \n" +
              " DBMS_OUTPUT.PUT_LINE('Cell Failure Coverage Freespace Failures Detected. Warning Message Follows.'); \n" +
              " DBMS_OUTPUT.PUT_LINE(v_cfc_fail_msg); \n" +
           " END IF; \n" +
        "  \n" +
           " DBMS_OUTPUT.PUT_LINE('.  .  .'); \n" +
           " DBMS_OUTPUT.PUT_LINE('Script completed.'); \n" +
        "  \n" +
            " DBMS_OUTPUT.PUT_LINE('dg_name: ' || dg_name); \n" +
            " DBMS_OUTPUT.PUT_LINE('dg_num_disks: ' || dg_num_disks); \n" +
            " DBMS_OUTPUT.PUT_LINE('dg_max_tot_mb: '  || dg_max_tot_mb); \n" +
            " DBMS_OUTPUT.PUT_LINE('dg_tot_mb: '  || dg_tot_mb); \n" +
            " DBMS_OUTPUT.PUT_LINE('dg_tot_mb_used: '  || dg_tot_mb_used); \n" +
            " DBMS_OUTPUT.PUT_LINE('dg_tot_mb_free: '  || dg_tot_mb_free); \n" +
        " ? := 'dg_name: ' || dg_name; \n" +
        " ? := 'dg_num_disks: ' || dg_num_disks; \n" +
        " ? := 'dg_max_tot_mb: ' || dg_max_tot_mb; \n" +
        " ? := 'dg_tot_mb: ' || dg_tot_mb; \n" +
        " ? := 'dg_tot_mb_used: ' || dg_tot_mb_used; \n" +
        " ? := 'dg_tot_mb_free: ' || dg_tot_mb_free; \n" +
        " ? := 'dg_tot_pct_free: ' || dg_tot_pct_free; \n" +
        "  \n" +
        "  \n" +
        " END; ";


        return sqlString;        
    }
    
    public static String getTopWaitEventsSQL(long dbId, long startSnapId, long endSnapId) {
        String sqlString = 
        " SELECT snap_id, " +
        "   wait_class, " +
        "   event_name, " +
        "   pctdbt, " +
        "   total_time_s " +
        " FROM " +
        "   (SELECT a.snap_id, " +
        "     wait_class, " +
        "     event_name, " +
        "     b.dbt, " +
        "     ROUND(SUM(a.ttm) /b.dbt*100,2) pctdbt, " +
        "     SUM(a.ttm) total_time_s, " +
        "     dense_rank() over (partition BY a.snap_id order by SUM(a.ttm)/b.dbt*100 DESC nulls last) rnk " +
        "   FROM " +
        "     (SELECT snap_id, " +
        "       wait_class, " +
        "       event_name, " +
        "       ttm " +
        "     FROM " +
        "       (SELECT " +
        "         /*+ qb_name(systemevents) */ " +
        "         (CAST (s.end_interval_time AS DATE) - CAST (s.begin_interval_time AS DATE)) * 24 * 3600 ela, " +
        "         s.snap_id, " +
        "         wait_class, " +
        "         e.event_name, " +
        "         CASE " +
        "           WHEN s.begin_interval_time = s.startup_time " +
        "           THEN e.time_waited_micro " +
        "           ELSE e.time_waited_micro - lag (e.time_waited_micro ) over (partition BY e.instance_number,e.event_name order by e.snap_id) " +
        "         END ttm " +
        "       FROM dba_hist_snapshot s, " +
        "         dba_hist_system_event e " +
        "       WHERE s.dbid          = e.dbid " +
        "       AND s.dbid            = " + dbId  +
        "       AND s.instance_number = e.instance_number " +
        "       AND s.snap_id         = e.snap_id " +
        "       AND s.snap_id BETWEEN " + startSnapId + " and " + endSnapId +
        "       AND e.wait_class != 'Idle' " +
        "       UNION ALL " +
        "       SELECT " +
        "         /*+ qb_name(dbcpu) */ " +
        "         (CAST (s.end_interval_time AS DATE) - CAST (s.begin_interval_time AS DATE)) * 24 * 3600 ela, " +
        "         s.snap_id, " +
        "         t.stat_name wait_class, " +
        "         t.stat_name event_name, " +
        "         CASE " +
        "           WHEN s.begin_interval_time = s.startup_time " +
        "           THEN t.value " +
        "           ELSE t.value - lag (t.value ) over (partition BY s.instance_number order by s.snap_id) " +
        "         END ttm " +
        "       FROM dba_hist_snapshot s, " +
        "         dba_hist_sys_time_model t " +
        "       WHERE s.dbid          = t.dbid " +
        "       AND s.dbid            = " + dbId +
        "       AND s.instance_number = t.instance_number " +
        "       AND s.snap_id         = t.snap_id " +
        "       AND s.snap_id BETWEEN " + startSnapId + " and " + endSnapId +
        "       AND t.stat_name = 'DB CPU' " +
        "       ) " +
        "     ) a, " +
        "     (SELECT snap_id, " +
        "       SUM(dbt) dbt " +
        "     FROM " +
        "       (SELECT " +
        "         /*+ qb_name(dbtime) */ " +
        "         s.snap_id, " +
        "         t.instance_number, " +
        "         t.stat_name nm, " +
        "         CASE " +
        "           WHEN s.begin_interval_time = s.startup_time " +
        "           THEN t.value " +
        "           ELSE t.value - lag (t.value ) over (partition BY s.instance_number order by s.snap_id) " +
        "         END dbt " +
        "       FROM dba_hist_snapshot s, " +
        "         dba_hist_sys_time_model t " +
        "       WHERE s.dbid          = t.dbid " +
        "       AND s.dbid            = " + dbId +
        "       AND s.instance_number = t.instance_number " +
        "       AND s.snap_id         = t.snap_id " +
        "       AND s.snap_id BETWEEN " + startSnapId + " and " + endSnapId +
        "       AND t.stat_name = 'DB time' " +
        "       ORDER BY s.snap_id, " +
        "         s.instance_number " +
        "       ) " +
        "     GROUP BY snap_id " +
        "     HAVING SUM(dbt) > 0 " +
        "     ) b " +
        "   WHERE a.snap_id = b.snap_id " +
        "   GROUP BY a.snap_id, " +
        "     a.wait_class, " +
        "     a.event_name, " +
        "     b.dbt " +
        "   ) " +
        " WHERE pctdbt > 0 " +
        " AND rnk     <= 5 " +
        " ORDER BY snap_id, " +
        "   pctdbt DESC  ";
        return sqlString;
    }
    
    public static String getOSStatistics(long dbId, long startSnapId, long endSnapId) {
        
        String sqlString = 
            " DECLARE \n" +
            "     l_pad_length number :=60; \n" +
            "       l_hosts varchar2(4000); \n" +
            "       l_dbid  number; \n" +
            " BEGIN \n" +
            "  \n" +
            "  \n" +
            "     dbms_output.put_line('~~BEGIN-OS-INFORMATION~~'); \n" +
            "     dbms_output.put_line(rpad('STAT_NAME',l_pad_length)||' '||'STAT_VALUE'); \n" +
            "     dbms_output.put_line(rpad('-',l_pad_length,'-')||' '||rpad('-',l_pad_length,'-')); \n" +
                "  \n" +
            "     FOR c1 IN ( \n" +
            "                       with inst as ( \n" +
            "               select min(instance_number) inst_num \n" +
            "                 from dba_hist_snapshot \n" +
            "                 where dbid = " + dbId + " \n" +
            "                       and snap_id BETWEEN to_number(" + startSnapId + ") and to_number(" + endSnapId + ")) \n" +
            "       SELECT  \n" +
            "                       CASE WHEN stat_name = 'PHYSICAL_MEMORY_BYTES' THEN 'PHYSICAL_MEMORY_GB' ELSE stat_name END stat_name, \n" +
            "                       CASE WHEN stat_name IN ('PHYSICAL_MEMORY_BYTES') THEN round(VALUE/1024/1024/1024,2) ELSE VALUE END stat_value \n" +
            "                   FROM dba_hist_osstat  \n" +
            "                  WHERE dbid = " + dbId + "  \n" +
            "                    AND snap_id = (SELECT MAX(snap_id) FROM dba_hist_osstat WHERE dbid = " + dbId + " AND instance_number = (select inst_num from inst)) \n" +
            "                                  AND instance_number = (select inst_num from inst) \n" +
            "                    AND (stat_name LIKE 'NUM_CPU%' \n" +
            "                    OR stat_name IN ('PHYSICAL_MEMORY_BYTES'))) \n" +
            "     loop \n" +
            "         dbms_output.put_line(rpad(c1.stat_name,l_pad_length)||' '||c1.stat_value); \n" +
            "     end loop; --c1 \n" +
                "  \n" +
                    " for c1 in (SELECT CPU_COUNT,CPU_CORE_COUNT,CPU_SOCKET_COUNT \n" +
            "                                FROM DBA_CPU_USAGE_STATISTICS  \n" +
            "                               where dbid = " + dbId + " \n" +
            "                                 and TIMESTAMP = (select max(TIMESTAMP) from DBA_CPU_USAGE_STATISTICS where dbid = " + dbId + " ) \n" +
            "                                 AND ROWNUM = 1) \n" +
            "       loop \n" +
                            " dbms_output.put_line(rpad('!CPU_COUNT',l_pad_length)||' '||c1.CPU_COUNT); \n" +
            "               dbms_output.put_line(rpad('!CPU_CORE_COUNT',l_pad_length)||' '||c1.CPU_CORE_COUNT); \n" +
            "               dbms_output.put_line(rpad('!CPU_SOCKET_COUNT',l_pad_length)||' '||c1.CPU_SOCKET_COUNT); \n" +
            "       end loop; \n" +
                    "  \n" +
            "       for c1 in (SELECT distinct platform_name FROM sys.GV_$DATABASE  \n" +
            "                               where dbid = " + dbId + " \n" +
            "                               and rownum = 1) \n" +
            "       loop \n" +
                            " dbms_output.put_line(rpad('!PLATFORM_NAME',l_pad_length)||' '||c1.platform_name); \n" +
            "       end loop; \n" +
            "  \n" +
            "        \n" +
                    "  \n" +
            "       FOR c2 IN (SELECT  \n" +
            "                                               $IF $$VER_GTE_11_2 $THEN \n" +
            "                                                       REPLACE(platform_name,' ','_') platform_name, \n" +
            "                                               $ELSE \n" +
                                                                    " 'None' platform_name, \n" +
            "                                               $END \n" +
            "                                               VERSION,db_name,DBID FROM dba_hist_database_instance  \n" +
            "                                               WHERE dbid = " + dbId + "   \n" +
            "                                               and startup_time = (select max(startup_time) from dba_hist_database_instance WHERE dbid = " + dbId + " ) \n" +
            "                                               AND ROWNUM = 1) \n" +
            "     loop \n" +
            "         dbms_output.put_line(rpad('PLATFORM_NAME',l_pad_length)||' '||c2.platform_name); \n" +
            "         dbms_output.put_line(rpad('VERSION',l_pad_length)||' '||c2.VERSION); \n" +
            "         dbms_output.put_line(rpad('DB_NAME',l_pad_length)||' '||c2.db_name); \n" +
            "         dbms_output.put_line(rpad('DBID',l_pad_length)||' '||c2.DBID); \n" +
            "     end loop; --c2 \n" +
                "  \n" +
            "     FOR c3 IN (SELECT count(distinct s.instance_number) instances \n" +
            "                            FROM dba_hist_database_instance i,dba_hist_snapshot s \n" +
            "                               WHERE i.dbid = s.dbid \n" +
            "                                 and i.dbid = " + dbId + " \n" +
            "                                 AND s.snap_id BETWEEN " + startSnapId + " AND " + endSnapId + ") \n" +
            "     loop \n" +
            "         dbms_output.put_line(rpad('INSTANCES',l_pad_length)||' '||c3.instances); \n" +
            "     end loop; --c3            \n" +
                    "  \n" +
                    "  \n" +
            "       FOR c4 IN (SELECT distinct regexp_replace(host_name,'^([[:alnum:]]+)\\..*$','\1')  host_name  \n" +
            "                            FROM dba_hist_database_instance i,dba_hist_snapshot s \n" +
            "                               WHERE i.dbid = s.dbid \n" +
            "                                 and i.dbid = " + dbId + " \n" +
            "                   and s.startup_time = i.startup_time \n" +
            "                                 AND s.snap_id BETWEEN " + startSnapId + " AND " + endSnapId + " \n" +
            "                           order by 1) \n" +
            "     loop \n" +
            "           l_hosts := l_hosts || c4.host_name ||',';        \n" +
            "       end loop; --c4 \n" +
            "       l_hosts := rtrim(l_hosts,','); \n" +
            "       dbms_output.put_line(rpad('HOSTS',l_pad_length)||' '||l_hosts); \n" +
                    "  \n" +
            "       FOR c5 IN (SELECT sys_context('USERENV', 'MODULE') module FROM DUAL) \n" +
            "     loop \n" +
            "         dbms_output.put_line(rpad('MODULE',l_pad_length)||' '||c5.module); \n" +
            "     end loop; --c5   \n" +
                    "  \n" +
                    "  \n" +
                    "  \n" +
            "       dbms_output.put_line('~~END-OS-INFORMATION~~'); \n" +
            " END; \n";
        
        return sqlString;
    }
  
  public static String getSizeOnDiskSQL(long dbId, long startSnapId, long endSnapId) {  
      String sql = 
          "    WITH ts_info as ( \n" +
          "    select dbid, ts#, tsname, max(block_size) block_size \n" +
          "    from dba_hist_datafile \n" +
          "    where dbid = " + dbId + " \n" +
          "    group by dbid, ts#, tsname), \n" +
          "    -- Get the maximum snaphsot id for each day from dba_hist_snapshot \n" +
          "    snap_info as ( \n" +
          "    select dbid,to_char(trunc(end_interval_time,'DD'),'MM/DD/YY') dd, max(s.snap_id) snap_id \n" +
          "    FROM dba_hist_snapshot s \n" +
          "    where s.snap_id between " + startSnapId + " and " + endSnapId + " \n" +
          "    and dbid = " + dbId + " \n" +
          "    --where s.end_interval_time > to_date(:start_time,'MMDDYYYY') \n" +
          "    --and s.end_interval_time < to_date(:end_time,'MMDDYYYY') \n" +
          "    group by dbid,trunc(end_interval_time,'DD')) \n" +
          "    -- Sum up the sizes of all the tablespaces for the last snapshot of each day \n" +
          "    select s.snap_id, round(sum(tablespace_size*f.block_size)/1024/1024/1024,2) size_gb \n" +
          "    from dba_hist_tbspc_space_usage sp, \n" +
          "    ts_info f, \n" +
          "    snap_info s \n" +
          "    WHERE s.dbid = sp.dbid \n" +
          "    AND s.dbid = " + dbId + " \n" +
          "     and s.snap_id between " + startSnapId + " and " + endSnapId + " \n" +
          "    and s.snap_id = sp.snap_id \n" +
          "    and sp.dbid = f.dbid \n" +
          "    AND sp.tablespace_id = f.ts# \n" +
          "    GROUP BY  s.snap_id,s.dd, s.dbid \n" +
          "    order by  s.snap_id \n";
      return sql;
  }
    
    /*
    -- ##############################################################################################

    REPHEADER PAGE LEFT '~~BEGIN-MEMORY-SGA-ADVICE~~'
    REPFOOTER PAGE LEFT '~~END-MEMORY-SGA-ADVICE~~'

    select snap_id,instance_number,sga_target_gb,size_factor,ESTD_PHYSICAL_READS,lead_read_diff
    from(
    with top_n_dbtime as(
    select snap_id from(
    select snap_id, sum(average) dbtime_p_s,
      dense_rank() over (order by sum(average) desc nulls last) rnk
     from dba_hist_sysmetric_summary
    where dbid = &DBID
     and snap_id between &SNAP_ID_MIN and &SNAP_ID_MAX
     and metric_name = 'Database Time Per Sec'
     group by snap_id)
     where rnk <= 5)
    SELECT a.SNAP_ID,
      INSTANCE_NUMBER,
      ROUND(sga_size/1024,1) sga_target_gb,
      sga_size_FACTOR size_factor,
      ESTD_PHYSICAL_READS,
      round((ESTD_PHYSICAL_READS - lead(ESTD_PHYSICAL_READS,1,ESTD_PHYSICAL_READS) over (partition by a.snap_id,instance_number order by sga_size_FACTOR asc nulls last)),1) lead_read_diff,
      min(sga_size_FACTOR) over (partition by a.snap_id,instance_number) min_factor,
      max(sga_size_FACTOR) over (partition by a.snap_id,instance_number) max_factor
    FROM DBA_HIST_SGA_TARGET_ADVICE a,top_n_dbtime tn
    WHERE dbid          = &DBID
    AND a.snap_id         = tn.snap_id)
    where (size_factor = 1
    or size_factor = min_factor
    or size_factor = max_factor
    or lead_read_diff > 1)
    order by snap_id asc,instance_number, size_factor asc nulls last;


    prompt 
    prompt 

    -- ##############################################################################################


    REPHEADER PAGE LEFT '~~BEGIN-MEMORY-PGA-ADVICE~~'
    REPFOOTER PAGE LEFT '~~END-MEMORY-PGA-ADVICE~~'


    SELECT SNAP_ID,
      INSTANCE_NUMBER,
      PGA_TARGET_GB,
      SIZE_FACTOR,
      ESTD_EXTRA_MB_RW,
      LEAD_SIZE_DIFF_MB,
      ESTD_PGA_CACHE_HIT_PERCENTAGE
    FROM
      ( WITH top_n_dbtime AS
      (SELECT snap_id
      FROM
        (SELECT snap_id,
          SUM(average) dbtime_p_s,
          dense_rank() over (order by SUM(average) DESC nulls last) rnk
        FROM dba_hist_sysmetric_summary
          where dbid = &DBID
          and snap_id between &SNAP_ID_MIN and &SNAP_ID_MAX
        AND metric_name = 'Database Time Per Sec'
        GROUP BY snap_id
        )
      WHERE rnk <= 5
      )
    SELECT a.SNAP_ID,
      INSTANCE_NUMBER,
      ROUND(PGA_TARGET_FOR_ESTIMATE/1024/1024/1024,1) pga_target_gb,
      PGA_TARGET_FACTOR size_factor,
      ROUND(ESTD_EXTRA_BYTES_RW  /1024/1024,1) ESTD_EXTRA_MB_RW,
      ROUND((ESTD_EXTRA_BYTES_RW - lead(ESTD_EXTRA_BYTES_RW,1,ESTD_EXTRA_BYTES_RW) over (partition BY a.snap_id,instance_number order by PGA_TARGET_FACTOR ASC nulls last))/1024/1024,1) lead_size_diff_mb,
      ESTD_PGA_CACHE_HIT_PERCENTAGE,
      MIN(PGA_TARGET_FACTOR) over (partition BY a.snap_id,instance_number) min_factor,
      MAX(PGA_TARGET_FACTOR) over (partition BY a.snap_id,instance_number) max_factor
    FROM DBA_HIST_PGA_TARGET_ADVICE a,
      top_n_dbtime tn
    WHERE dbid = &DBID
    AND a.snap_id = tn.snap_id
      )
    WHERE (size_factor   = 1
    OR size_factor       = min_factor
    OR size_factor       = max_factor
    OR lead_size_diff_mb > 1)
    ORDER BY snap_id ASC,
      instance_number,
      size_factor ASC nulls last;


    prompt 
    prompt 

    -- ##############################################################################################

     */
}
