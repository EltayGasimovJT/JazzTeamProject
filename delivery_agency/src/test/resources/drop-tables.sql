SET REFERENTIAL_INTEGRITY FALSE;
SHOW TABLES;
truncate table roles_allowed_put_to_state;
truncate table roles_allowed_withdraw_from_state;
truncate table worker_roles;
truncate table order_history;
truncate table warehouse;
truncate table order_processing_point;
truncate table abstract_building;
truncate table voyage;
truncate table abstract_location;
truncate table orders_ticket;
truncate table orders;
truncate table parcel_parameters;
truncate table clients_code;
truncate table clients;
truncate table coefficient_for_price_calculation;
truncate table order_state;
SET REFERENTIAL_INTEGRITY TRUE;