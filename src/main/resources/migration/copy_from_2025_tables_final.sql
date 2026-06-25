-- Final Migration Script - Simple and Robust
-- Copies data from ALL tables with "2025" to their base tables

DO $$
DECLARE
    table_record RECORD;
    base_table_name TEXT;
    source_table_name TEXT;
    column_list TEXT;
    conflict_column TEXT;
    sql TEXT;
    table_count INTEGER := 0;
    has_unique_constraint BOOLEAN;
BEGIN
    RAISE NOTICE '========================================';
    RAISE NOTICE 'Finding tables with 2025 in the name...';
    RAISE NOTICE '========================================';
    
    FOR table_record IN 
        SELECT table_name, table_schema
        FROM information_schema.tables 
        WHERE table_schema IN ('public', 'admissions')
          AND table_name LIKE '%2025%'
        ORDER BY table_schema, table_name
    LOOP
        table_count := table_count + 1;
        source_table_name := table_record.table_name;
        
        RAISE NOTICE 'Found: %.%', table_record.table_schema, source_table_name;
        
        -- Determine base table name
        base_table_name := REPLACE(source_table_name, '2025', '');
        base_table_name := RTRIM(base_table_name, '_');
        base_table_name := LTRIM(base_table_name, '_');
        base_table_name := REGEXP_REPLACE(base_table_name, 'phase[0-9]+$', '');
        base_table_name := RTRIM(base_table_name, '_');
        
        RAISE NOTICE '  Target table: %.%', table_record.table_schema, base_table_name;
        
        -- Check if base table exists
        IF NOT EXISTS (SELECT 1 FROM information_schema.tables 
                       WHERE table_schema = table_record.table_schema 
                       AND table_name = base_table_name) THEN
            RAISE NOTICE '  WARNING: Base table %.% does not exist, creating it...', table_record.table_schema, base_table_name;
            
            -- Create the base table by copying structure from 2025 table
            sql := format('CREATE TABLE %I.%I (LIKE %I.%I INCLUDING ALL)',
                          table_record.table_schema, base_table_name,
                          table_record.table_schema, source_table_name);
            
            BEGIN
                EXECUTE sql;
                RAISE NOTICE '  SUCCESS: Created table %.%', table_record.table_schema, base_table_name;
            EXCEPTION
                WHEN OTHERS THEN
                    RAISE NOTICE '  ERROR creating table: % - %', SQLSTATE, SQLERRM;
                    CONTINUE;
            END;
        END IF;
        
        -- Build column list (with proper quoting for reserved words)
        -- Filter out empty column names to avoid "zero-length delimited identifier" error
        SELECT string_agg(format('"%I"', column_name), ', ' ORDER BY ordinal_position) INTO column_list
        FROM information_schema.columns
        WHERE table_schema = table_record.table_schema
          AND table_name = source_table_name
          AND column_name IS NOT NULL 
          AND TRIM(column_name) != '';
        
        IF column_list IS NULL OR column_list = '' THEN
            RAISE NOTICE '  WARNING: No columns found, skipping';
            CONTINUE;
        END IF;
        
        -- Try simple INSERT first (no ON CONFLICT)
        -- This will work if tables are empty or if you want to just add data
        sql := format('INSERT INTO %I.%I (%s) SELECT %s FROM %I.%I',
                      table_record.table_schema, base_table_name, column_list, column_list,
                      table_record.table_schema, source_table_name);
        
        RAISE NOTICE '  Executing simple INSERT...';
        
        BEGIN
            EXECUTE sql;
            GET DIAGNOSTICS table_count := ROW_COUNT;
            RAISE NOTICE '  SUCCESS: Inserted % rows', table_count;
        EXCEPTION
            WHEN OTHERS THEN
                RAISE NOTICE '  ERROR: % - %', SQLSTATE, SQLERRM;
        END;
        
        RAISE NOTICE '';
    END LOOP;
    
    RAISE NOTICE '========================================';
    RAISE NOTICE 'Migration Summary';
    RAISE NOTICE '========================================';
    RAISE NOTICE 'Total tables processed: %', table_count;
    RAISE NOTICE 'Migration completed!';
    RAISE NOTICE '========================================';
    
END $$;

-- Verification
DO $$
DECLARE
    table_record RECORD;
    base_table_name TEXT;
    row_count INTEGER;
BEGIN
    RAISE NOTICE '';
    RAISE NOTICE '========================================';
    RAISE NOTICE 'Verification - Record Counts';
    RAISE NOTICE '========================================';
    
    FOR table_record IN 
        SELECT table_name, table_schema
        FROM information_schema.tables 
        WHERE table_schema IN ('public', 'admissions')
          AND table_name LIKE '%2025%'
        ORDER BY table_schema, table_name
    LOOP
        base_table_name := REPLACE(table_record.table_name, '2025', '');
        base_table_name := RTRIM(base_table_name, '_');
        base_table_name := LTRIM(base_table_name, '_');
        base_table_name := REGEXP_REPLACE(base_table_name, 'phase[0-9]+$', '');
        base_table_name := RTRIM(base_table_name, '_');
        
        BEGIN
            EXECUTE format('SELECT COUNT(*) FROM %I.%I', table_record.table_schema, base_table_name) INTO row_count;
            RAISE NOTICE '%.%: % rows', table_record.table_schema, base_table_name, row_count;
        EXCEPTION
            WHEN OTHERS THEN
                RAISE NOTICE '%.%: Could not count', table_record.table_schema, base_table_name;
        END;
    END LOOP;
    
    RAISE NOTICE '========================================';
END $$;