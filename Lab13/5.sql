use examplechat;
select users.id from users
inner join messages
on users.id=messages.user_id
group by users.id
having count(messages.user_id)>1;
