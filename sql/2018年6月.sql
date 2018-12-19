use sHouseApp_pre;
update user_roles_relations
set last_login_date = update_time
where last_login_date is null
;