alter table emp add constraint emp_pk primary key (num);
alter table dept add constraint dept_pk primary key (n_dept);
alter table emp add constraint emp_fk1 foreign key (n_sup) references emp(num) on delete cascade;
alter table emp add constraint emp_fk2 foreign key (n_dept) references dept(n_dept) on delete cascade;