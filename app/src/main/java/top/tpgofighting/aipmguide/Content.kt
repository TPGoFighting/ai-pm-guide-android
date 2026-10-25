package top.tpgofighting.aipmguide

data class Chapter(val id: String, val num: String, val kicker: String, val title: String, val intro: String)
data class Group(val id: String, val title: String, val intro: String, val chapters: List<String>)
data class Takeaway(val id: String, val idx: String, val title: String, val body: String, val tag: String)
data class Skill(val id: String, val name: String, val req: String, val proof: String, val priority: String)
data class ThinkStep(val name: String, val body: String)
data class ResumePanel(val id: String, val title: String, val qs: List<String>)
data class WorkStep(val id: String, val short: String, val title: String, val body: String)
data class PlanItem(val title: String, val desc: String)
data class Tip(val id: String, val idx: String, val title: String, val body: String)

val CHAPTERS = listOf(
    Chapter("takeaways", "01", "KEY TAKEAWAYS", "最重要的 5 个结论", "先记住这五件事，再决定下一步做什么。"),
    Chapter("skills", "02", "SKILL MAP", "产品经理能力模型", "六项能力不是孤立的，需要在项目中组合起来。"),
    Chapter("thinking", "03", "PRODUCT SENSE", "用一道估算题训练思维", "重点不是猜中一个精确数字，而是让假设、推导过程和验证方法清晰可见。"),
    Chapter("resume", "04", "RESUME STRATEGY", "把已有经历写成岗位证据", "不必推翻过往经历，重点是提炼与目标岗位相关的价值。"),
    Chapter("workflow", "05", "DAILY WORK", "产品经理的工作闭环", "闭环的意思就是：它真的会转起来。"),
    Chapter("plan", "06", "ACTION PLAN", "把建议变成行动", "可以直接勾选追踪进度。"),
    Chapter("details", "07", "DON'T MISS", "三个容易忽略的细节", "四张边角贴士，贴在手账边缘的那种。"),
)

val GROUPS = listOf(
    Group("direction", "方向", "先定方向：记住最重要的结论，再对照能力模型看自己缺什么。", listOf("takeaways", "skills")),
    Group("method", "方法", "怎么做事：用估算题练思维，用闭环跑日常工作。", listOf("thinking", "workflow")),
    Group("delivery", "执行", "动手落地：把经历写成证据，照清单推进，别漏了边角细节。", listOf("resume", "plan", "details")),
)

fun chapterOf(id: String) = CHAPTERS.first { it.id == id }
fun groupOfChapter(id: String) = GROUPS.first { it.chapters.contains(id) }

val TAKEAWAYS = listOf(
    Takeaway("t1", "TAKEAWAY 01", "不要做泛泛的产品经理", "明确细分方向，把产品能力、AI 开发和运营经历组合成有辨识度的岗位定位。", "岗位定位"),
    Takeaway("t2", "TAKEAWAY 02", "让作品替你说话", "真实可用、可以演示、能解释设计决策的项目，比只罗列技术名词更有说服力。", "作品集"),
    Takeaway("t3", "TAKEAWAY 03", "从用户出发思考", "先弄清用户是谁、需求是什么、为什么需要，再考虑功能设计和技术实现。", "用户思维"),
    Takeaway("t4", "TAKEAWAY 04", "持续理解 AI 新进展", "关注模型、Agent 和 AI 产品，能解释它们的能力、局限以及适合解决的问题。", "AI 应用"),
    Takeaway("t5", "TAKEAWAY 05", "推动方案真正落地", "产品工作贯穿需求调研、方案设计、研发协作、验收、反馈与迭代，不只是想点子或画原型。", "端到端交付"),
    Takeaway("principle", "THE PRINCIPLE", "匹配度比堆砌经历重要", "针对目标岗位组织经历，让招聘方快速看出你能解决什么问题，并用真实成果支撑。", "简历策略"),
)

val SKILLS = listOf(
    Skill("product", "产品思维", "理解用户痛点、分析需求价值、提出合理方案。", "讲清用户、场景、需求优先级与设计取舍。", "属于优先建立的三项优势之一：产品 Sense。"),
    Skill("ai", "AI 技术理解", "理解模型、AI 应用与 Agent 的能力及限制。", "展示真实 AI 项目，并说明技术选型理由。", "属于优先建立的三项优势之一：AI 前沿应用理解。"),
    Skill("analysis", "需求分析", "把模糊需求转成明确功能、流程和验收标准。", "需求文档、流程图、用户故事或验收清单。", ""),
    Skill("design", "产品设计", "理解原型、UI/UX、交互、页面布局与体验。", "可点击原型或可实际体验的网页产品。", ""),
    Skill("business", "商业意识", "评估投入产出、开发成本、业务价值与优先级。", "说明为什么做、先做什么、如何衡量效果。", ""),
    Skill("comm", "沟通协作", "连接业务与研发，推动跨团队问题解决。", "真实协作案例、冲突处理和交付结果。", ""),
)
const val SKILL_CALLOUT = "优先建立的三项优势：产品 Sense、AI 前沿应用理解、能展示实际成果的项目。这三者组合起来，比只掌握一堆术语更有说服力。"

val THINK_STEPS = listOf(
    ThinkStep("需求侧", "估算潜在消费者、不同人群的购买概率与消费场景。"),
    ThinkStep("区域分层", "区分商务区、校园、居民区、旅游区等，避免假设各区域相同。"),
    ThinkStep("供给侧", "估算门店、摊位、餐车数量，以及营业时间和单店销量。"),
    ThinkStep("建立模型", "按区域计算消费者规模 × 购买率，再用供给侧数据交叉验证。"),
    ThinkStep("给出区间", "解释关键假设、数据来源和误差，不编造精确数字。"),
)
const val THINK_METHOD = "核心方法：先需求侧，再供给侧。产品是给用户使用的。先理解用户需求，再看供给能力，最后用数据校验判断。结构化分析比迅速给出答案更重要。"

val RESUME_PANELS = listOf(
    ResumePanel("tech", "技术 / AI 项目", listOf("目标用户与真实问题是什么？", "为什么采用这套方案和技术？", "你负责了哪些关键决策？", "如何验证效果，有什么真实结果？")),
    ResumePanel("sales", "销售 / 运营经历", listOf("如何理解客户需求与用户反馈？", "如何组织获客、转化或运营流程？", "如何协调团队、推动任务完成？", "有哪些可核实的业务结果？")),
    ResumePanel("data", "数据库 / 行业项目", listOf("明确实际业务场景与使用者。", "解释数据流程和方案设计依据。", "说明落地过程、使用反馈与改进。", "涉及敏感数据时，确保授权与脱敏。")),
    ResumePanel("portfolio", "作品链接 / 作品集", listOf("确保链接可访问、演示路径清楚。", "先展示核心功能与关键使用场景。", "准备设计取舍、限制和后续迭代。", "每一条成果和数据都经得起追问。")),
)
val RESUME_CHAIN = listOf("业务背景", "用户痛点", "我的职责", "方案与取舍", "验证方式", "实际结果")
const val RESUME_CHAIN_NOTE = "不要只写“使用了某技术开发某系统”。"

val WORK_STEPS = listOf(
    WorkStep("align", "对齐需求", "对齐业务需求", "与业务方或客户沟通，弄清目标、用户问题、约束和成功标准。"),
    WorkStep("design", "拆解设计", "拆解并设计方案", "将抽象需求转化为功能、流程、原型、优先级和验收条件。"),
    WorkStep("evaluate", "评估可行", "评估可行性", "与研发讨论技术难点、资源和排期；必要时提出替代或降级方案。"),
    WorkStep("collab", "协作验收", "协作推进与验收", "跟进实现进度，测试功能，记录问题，推动修复并确认需求完成情况。"),
    WorkStep("iterate", "反馈迭代", "反馈并持续迭代", "向业务方说明交付结果和使用方式，收集反馈，判断后续改进优先级。"),
)
const val WORK_CALLOUT = "需要了解的协作基础：需求评审、会议对齐、工时与成本评估、Git 基础协作、测试 / 预发布 / 正式环境，以及使用 AI 辅助制作原型和验证交互。"

val PLAN_ITEMS = listOf(
    PlanItem("明确目标岗位", "收集 AI 产品、Agent 产品等岗位 JD，找出重复出现的能力要求。"),
    PlanItem("整理现有项目与经历", "筛选技术、数据库、AI、运营或销售经历，记录自己真实负责的部分。"),
    PlanItem("做出一个可演示的作品", "选定用户和问题，完成核心流程，准备可访问的演示链接。"),
    PlanItem("掌握产品工作流程", "练习需求拆解、PRD、原型、评审、研发协作和验收。"),
    PlanItem("建立 AI 信息跟踪习惯", "关注模型、Agent 与新产品，记录能力、成本、局限和使用场景。"),
    PlanItem("练习产品面试题", "覆盖估算题、需求分析、产品设计、项目深挖和协作案例。"),
    PlanItem("打磨简历和作品集", "围绕目标 JD，清楚展示用户、问题、方案、验证与实际成果。"),
)

val TIPS = listOf(
    Tip("prototype", "DETAIL 01", "AI 原型与前端实践", "懂页面布局、交互、UI/UX，并能借助 AI 快速制作原型或修改小功能，有助于更快验证产品方案。"),
    Tip("terms", "DETAIL 02", "术语要会用，不只是背诵", "理解转化率、留存率、渗透率、需求优先级等概念，并能结合实际项目说明其含义与作用。"),
    Tip("devops", "DETAIL 03", "理解开发与发布协作", "了解分支协作、代码冲突、测试、预发布、正式发布和验收流程，便于与研发团队协作。"),
    Tip("interview", "INTERVIEW NOTE", "用 AI 模拟面试，但要自己推理", "可以让 AI 出题、追问和整理术语；先独立作答，再复盘思路，避免只背标准答案。"),
)

const val HERO_INTRO = "一份基于求职经验交流整理的行动指南。聚焦岗位定位、项目作品、产品思维与面试准备，把已有经历转化成清晰、可信的求职竞争力。"
val HERO_FORMULA = listOf("技术背景", "理解用户", "判断需求", "设计方案", "快速验证", "推动落地")
const val HERO_CORE = "不只证明你会做技术，更要证明你能解决问题。"
const val HERO_CORE_CHAIN = "理解用户 · 判断需求 · 设计方案 · 快速验证 · 推动落地"
val HERO_CORE_TAGS = listOf("用户思维", "AI 应用", "真实作品", "协作落地")
val HERO_ROUTE = listOf("01 / 定位" to "明确目标细分岗位", "02 / 作品" to "用真实项目证明能力", "03 / 面试" to "讲清思路与取舍")
const val FINALE_QUOTE = "不要只证明自己是一个懂 AI 的技术学生，而要证明自己能理解用户、判断需求、设计方案、借助 AI 快速验证，并推动产品真正落地。"
const val DISCLAIMER = "内容依据求职经验交流录音整理。招聘流程和岗位偏好因公司、团队与时期而异；学长个人经历可作参考，不代表所有公司的统一标准。"
