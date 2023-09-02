explain select count(*)
from post;

explain SELECT createdAt, memberId, count(id)
FROM post
where memberId = 4 and createdDate between '1900-01-01' and '2023-08-30'
GROUP BY memberId, createdAt;

select memberId, count(id)
from post
group by memberId;

select createdDate, count(id)
from post
group by createdDate
order by 2 desc;

explain select count(distinct createdDate)
from post;

select *
from post
where memberId = 4
order by id desc, contents desc
limit 2
offset 4;

select *
from post
where memberId = 4 and id > 2;