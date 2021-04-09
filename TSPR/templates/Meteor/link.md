## 🏷 **链接“ {{.title}}”的内容**{: style="background-image: linear-gradient(to right, var(--b3-theme-primary), var(--b3-theme-error)); -webkit-background-clip: text; color: transparent;"}
{: id="20210129211237-b25zmo0" }

!{{SELECT * FROM blocks WHERE (id in (select parent_id from blocks where markdown like '%{{.id}}%' and type='p') and type='i') or (id in (select parent_id from blocks where markdown like '%{{.id}}%' and type!='l' and type !='i') ) and root_id !='{{.id}}' order by created DESC}}
{: id="20210129211237-jh17cgr" updated="20210226135602"}

## 🏷 **提及“ {{.title}}”的内容**{: style="background-image: linear-gradient(to right, var(--b3-theme-primary), var(--b3-theme-error)); -webkit-background-clip: text; color: transparent;"}
{: id="20210117201946-iesarol"}

!{{SELECT * FROM blocks WHERE (type='i' and id in (SELECT parent_id FROM blocks WHERE type='p' and (content LIKE '%{{.title}}%' or name LIKE '%{{.title}}%' or alias LIKE '%{{.title}}%' or ('{{.alias}}' !='' and content LIKE '%{{.alias}}%') or ('{{.alias}}' !='' and name LIKE '%{{.alias}}%') or ('{{.alias}}' !='' and alias LIKE '%{{.alias}}%'))) and markdown not LIKE '%{{.id}}%') or ( type !='l' and type != 'i' and id in (SELECT parent_id FROM blocks WHERE (content LIKE '%{{.title}}%' or name LIKE '%{{.title}}%' or alias LIKE '%{{.title}}%' or ('{{.alias}}' !='' and content LIKE '%{{.alias}}%') or ('{{.alias}}' !='' and name LIKE '%{{.alias}}%') or ('{{.alias}}' !='' and alias LIKE '%{{.alias}}%'))  ) and markdown not LIKE '%{{.id}}%' )and root_id !='{{.id}}' order by created DESC}}
{: id="20210225170009-gmypcj3" updated="20210226135612"}

{: id="20210226135604-quuu1q3"}


{: id="20210117192611-6c17hv1" type="doc"}
