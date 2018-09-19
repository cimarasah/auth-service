package com.cimarasah.auth.domain.repository.custom;

//@Repository
public class RedisTokenRepository {

//    private static final Logger logger = LoggerFactory.getLogger(RedisTokenRepository.class);
//
//	@Autowired
//	private RedisTemplate<String, Object> redisTemplate;
//
//	@Autowired
//	private RedisConfiguration redisConfiguration;
//
//	private HashOperations<String, String, Object> hashOps;
//
//	@PostConstruct
//    private void init() {
//        hashOps = redisTemplate.opsForHash();
//    }
//
//	@Override
//	public void save(String jwt, String username, Date validationDate) {
//        String key = keyProvider(username);
//        hashOps.put(key, "validationDate", validationDate);
//		hashOps.put(key, "token", jwt);
//		redisTemplate.expire(key, redisConfiguration.getTimeout(), redisConfiguration.getTimeUnit());
//	}
//
//	@Override
//	public Date findValidationDate(String token, String username) {
//        return (Date) hashOps.get(keyProvider(username), "validationDate");
//    }
//
//    @Override
//    public String findToken(String username) {
//        return (String) hashOps.get("jwt:" + username, "token");
//    }
//
//	@Override
//	public void delete(String token, String username) {
//        hashOps.delete(token, username);
//    }
//
//    private String keyProvider(String username) {
//	    return "jwt:" + username;
//    }
}
